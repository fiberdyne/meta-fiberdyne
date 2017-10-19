# Fiberdyne Yocto Layer
This is the Yocto meta layer for the Fiberdyne DSP, for use with [Automotive Grade Linux (AGL)](https://www.automotivelinux.org/) on the [Renesas R-Car M3 Pro](https://www.renesas.com/en-sg/solutions/automotive/products/rcar-m3.html) platform.

## Setup
__NOTE__: For all purposes, the Yocto build root directory will be referred to as `$WORK`.

You can include the layer in your Yocto project in one of two ways:

### Install from release
Download the latest release archive from [Releases](https://github.com/fiberdyne/meta-fiberdyne/releases), and extract to `$WORK/meta/meta-fiberdyne`
### OR
### Clone from repository
Make sure that [Git LFS](https://git-lfs.github.com/) is installed on your computer.
Clone the meta-fiberdyne repository into `$WORK/meta` using Git LFS:
```sh
$ cd $WORK/meta && git lfs clone https://github.com/fiberdyne/meta-fiberdyne.git
```
Now we must build it in to Yocto:


## Building
Locate your Yocto layers configuration file at `$WORK/build/conf/bblayers.conf` and add the following:

```sh
BBLAYERS_append = " ${METADIR}/../meta-fiberdyne"
```
Locate your Yocto local configuration file at `$WORK/build/conf/local.conf` and add the following:
```sh
IMAGE_INSTALL_append = " fd-dsp"
```
Build your AGL image.


## Running
Once your image is built you can boot it either via SD card, or using a netboot scheme.  See [here](http://docs.automotivelinux.org/docs/getting_started/en/dev/reference/machines/R-Car-Starter-Kit-gen3.html) for more information on booting AGL images.

After booting into AGL we need to install our apps. Run the following:
```sh
afm-util install fd-dsp-service.wgt
afm-util install fd-dsp.wgt
```

Reboot, and the Fiberdyne DSP AGL service deamon should now be running. This can be tested by running the following, and noting it's output:
```sh
journalctl | grep afb-daemon
```

To launch the UI, we can tap the HVAC icon on the AGL HomeScreen.

__NOTE__: We currently hijack the HVAC icon, but in future HomeScreen updates we will not have to.


## Playing Audio
Currently, stereo audio is output on the line out auxilliary jack. We can play audio by looking at the ALSA, PulseAudio, or GStreamer interfaces.

### ALSA
The underlying [ALSA](https://www.alsa-project.org/main/index.php/Main_Page) driver has been used to define three audio subdevices that support simultaneous playback within multiple processes. The subdevices are:

|_Subdevice_|           _Description_            | _ALSA Subdevice_ |
|:---------:|------------------------------------|:----------------:|
| 0         | Dual (stereo) channel 16-bit input | `hw:1,0,0`
| 1         | Single (mono) channel 16-bit input | `hw:1,0,1`
| 2         | Single (mono) channel 16-bit input | `hw:1,0,2`

__NOTE__: Currently, only a 48000 Hz sample rate is supported.

You can use the standard ALSA tool, `aplay` to test audio to a subdevice.  For example, the following will play a `.wav` file to the dual channel (stereo) subdevice:
```sh
$ aplay -D hw:1,0,0 audiofile.wav
```

### PulseAudio
To play using [PulseAudio](https://www.freedesktop.org/wiki/Software/PulseAudio/), you can list the available sinks using:
```sh
$ pactl list sinks
```
From this list, you will have a number of PulseAudio sink indexes. Simply stream to a given sink index using `paplay`.  For example, to stream to the sink index 1, do:
```sh
$ paplay -d 1 audiofile.wav
```

__NOTE__: The mono sinks have a tendency to disappear for unknown reasons.  If this happens, simply run the following to refresh them:
```sh
$ systemctl --user start fd-dsp-pulse
```

__NOTE__: If you experience crackling audio, you can modify  a pulseaudio parameter is `/etc/pulse/default.pa`. Change the following line:
```sh
load-module module-detect
```
to:
```sh
load-module module-detect tsched=0
```

### GStreamer
To play using [GStreamer](https://gstreamer.freedesktop.org/), you must first set the default PulseAudio sink:
```sh
$ pactl set-default-sink <sink-name>
```
Then you can do the following to play to the default PulseAudio sink using GStreamer:
```sh
$ gst-play-1.0 audiofile.wav ! pulsesink
```

### Codecs
The `xf_audioplayer` application can be used as a reference to play PCM, AAC (ADTS only), and MP3 streams to subdevice 0, with decoding performed on the ADSP core. For example, to play an MP3 file:
```sh
$ xf_audioplayer hw:1,0 audiofile.mp3 mp3
```
Note that the all streams must be 48000Hz and no sample rate conversion is provided.
Run `xf_audioplayer` without arguments for usage instructions.
