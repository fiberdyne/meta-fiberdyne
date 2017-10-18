# Fiberdyne Yocto Layer Setup
This tutorial will guide you in setting up the meta-fiberdyne Yocto layer for the Poky 
build environment for use with [AGL](https://www.automotivelinux.org/) on the Renesas M3 ULCB platform.
For all purposes, the Yocto root directory will be referred to as ``$WORK``.

## Setup
------
You can include the layer in your Yocto project in one of two ways:

#### Install from release
Download the latest release archive from [Releases](https://github.com/fiberdyne/meta-fiberdyne/releases), and extract to `$WORK/meta/meta-fiberdyne`
#### OR
#### Clone from repository
Make sure that [Git LFS](https://git-lfs.github.com/) is installed on your computer.
Clone the "m3ulcb" branch of the meta-fiberdyne repository into `$WORK/meta`:
   ```sh
   $ cd $WORK/meta && git lfs clone https://github.com/fiberdyne/meta-fiberdyne.git --branch m3ulcb
   ```

Now we must build it in to Yocto:
#### Building
Locate your Yocto `bblayers` configuration file at `$WORK/build/conf/bblayers.conf` and add the following lines:
   ```sh
   BBLAYERS_append = " ${METADIR}/../meta-fiberdyne"
   ```
   Locate your Yocto `local` configuration file at `$WORK/build/conf/local.conf` and add the following lines:
   ```sh
   IMAGE_INSTALL_append = " fd-dsp"
   ```
Build your AGL image.

#### Running
Once your image is built you can boot it either via SD card, or using a netboot scheme.  See [here](http://docs.automotivelinux.org/docs/getting_started/en/dev/reference/machines/R-Car-Starter-Kit-gen3.html) for more information on booting AGL images.
After booting into AGL, some modifications need to be made.  Add the following lines to `/etc/xdg/weston/weston.ini`:
   ```sh
   [launcher]
   icon=/usr/share/weston/icon_window.png
   path=/usr/bin/_fd-dsp-ui
   ```
   And change the shell variable to:
   ```sh
   $ shell=desktop-shell.so
   ```
   Then run:
   ```sh
   $ systemctl restart weston
   ```

   You will now have a launcher icon in the top left corner, and this is used to launch the fd-dsp UI.

Reboot, and the `fd-dsp` daemon should be running. There should be stereo audio coming from the LINE OUT auxillary.

Tap the icon to use the UI. If the UI does not alter the audio, REBOOT since the connection has failed.

[NOTE]
  It is possible to run the UI from the AGL HomeScreen, however there is currently a bug in it that prohibits
  aour package to be installed.  This will come in future updates.

#### System Audio Info
-------------------------------------------------------------------------------------------
ALSA has been used to define three audio subdevices that supports simultaneous playback 
within multiple processes. The streams are mixed inside the ADSP core. 

##### ALSA
The underlying [ALSA](https://www.alsa-project.org/main/index.php/Main_Page) driver supports 48000 Hz sample rate only. The subdevices are:

|_Subdevice_|           _Description_            | _ALSA Subdevice_ |
|:---------:|------------------------------------|:----------------:|
| 0         | Dual (stereo) channel 16-bit input | `hw:1,0,0`
| 1         | Single (mono) channel 16-bit input | `hw:1,0,1`
| 2         | Single (mono) channel 16-bit input | `hw:1,0,2`

You can use the standard ALSA tool, `aplay` to test audio to a subdevice.  For example, the following will play a `.wav` file to the dual channel (stereo) subdevice:
```sh
$ aplay -D hw:1,0,0 audiofile.wav
```

##### PulseAudio
To test using [PulseAudio](https://www.freedesktop.org/wiki/Software/PulseAudio/), you can list the available sinks using:
```sh
$ pactl list sinks
```
From this list, you will have a number of PulseAudio sink indexes. Simply stream to a given sink index using `paplay`.  For example, to stream to the sink index 1, do:
```sh
$ paplay -d 1 audiofile.wav
```

NOTE: The mono sinks have a tendency to disappear for unknown reasons.  If this happens, simply run the following to refresh them:
```sh
$ systemctl --user start fd-dsp-pulse
```

NOTE: If you experience crackling audio, you can modify  a pulseaudio parameter is `/etc/pulse/default.pa`. Change the following line:
```sh
load-module module-detect
```
to:
```sh
load-module module-detect tsched=0
```

##### GStreamer
To test using [GStreamer](https://gstreamer.freedesktop.org/), you must first set the default PulseAudio sink:
```sh
$ pactl set-default-sink <sink-name>
```
Then you can do the following to play to the default PulseAudio sink using GStreamer:
```sh
$ gst-play-1.0 audiofile.wav ! pulsesink
```

##### Codecs
The `xf_audioplayer` application can be used as a reference to play PCM, AAC (ADTS only), and MP3 streams to subdevice 0, with decoding performed on the ADSP core. For example, to play an MP3 file:
```sh
$ xf_audioplayer hw:1,0 audiofile.mp3 mp3
```
Note that the all streams are 48000Hz and no sample rate conversion is provided.
Run `xf_audioplayer` without arguments for usage instructions.
