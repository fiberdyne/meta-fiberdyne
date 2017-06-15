## Fiberdyne Yocto Layer Setup
This tutorial will guide you in setting up the meta-fiberdyne Yocto layer for the Poky 
build environment for use with [AGL](https://www.automotivelinux.org/) on the Renesas M3 ULCB board.
For all purposes, the Yocto root directory will be referred to as ``$WORK``.

#### Setup
------
1. Make sure that [Git LFS](https://git-lfs.github.com/) is installed:

   ```
   $ sudo add-apt-repository ppa:git-core/ppa
   ```
   ```
   $ curl -s https://packagecloud.io/install/repositories/github/git-lfs/script.deb.sh | sudo bash
   ```
   ```
   $ sudo apt-get install git-lfs
   ```
   
2. Clone the "m3ulcb" branch of the meta-fiberdyne repository into `$WORK/meta`:
   ```
   $ cd $WORK/meta && git lfs clone https://github.com/fiberdyne/meta-fiberdyne.git --branch m3ulcb
   ```
3. Locate your Yocto `.bblayers` configuration file at `$WORK/build/conf/bblayers.conf` and add the following lines:
   ```
   BBLAYERS_append = " ${METADIR}/../meta-fiberdyne"
   ```
   Locate your Yocto local configuration file at `$WORK/build/conf/local.conf` and add the following lines:
   ```
   IMAGE_INSTALL_append = " fd-dsp"
   ```
4. Build your AGL image.
5. Once your image is built, add the following lines to `/etc/xdg/weston/weston.ini`:
   ```
   [launcher]
   icon=/usr/share/weston/icon_window.png
   path=/usr/bin/_fd-dsp-ui
   ```
   And change the shell variable to:
   ```
   shell=desktop-shell.so
   ```
   Then run:
   ```
   $ systemctl restart weston
   ```

   You will now have a launcher icon in the top left corner, and this is used to launch the fd-dsp UI.

6. Reboot, and the fd-dsp` daemon should be running. There should be audio coming from the LINE OUT auxillary.

7. Tap the icon to use the UI. If the UI does not alter the audio, REBOOT since the connection has failed.

[NOTE]
  It is possible to run the UI from the AGL HomeScreen, however there is currently a bug in it that prohibits
  aour package to be installed.  This will come in future updates.
  
[NOTE]
  This layer renders the AGL-netboot feature unusable, due to a conflict in reserved memory

#### System Audio Info
-------------------------------------------------------------------------------------------
ALSA has been used to define three audio subdevices that supports simultaneous playback 
within multiple processes. 
The streams are mixed inside the ADSP core. 

The underlying ALSA driver supports 48000 KHz sample rate only.

1. Subdevice 0 :     Dual channel 16-bit input
2. Subdevice 1 :     Single channel 16-bit input
3. Subdevice 2 :     Single channel 16-bit input 

The test application fd-dsp-test uses the standard ALSA tool, aplay, to play stereo
audio using subdevice 0. 
The other subdevices can be tested via the same commands used in the test script. 


Furthermore 'xf_audioplayer' application can be used as a reference to play PCM, AAC (ADTS only) and MP3 streams to subdevice 0.
Note that the all streams are 48000KHz and no smaple rate conversion is provided.
