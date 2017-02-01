#!/bin/bash
amixer set -c 0 Headphone\ Enable 25 unmute
amixer set -c 0 LINEOUT\ Mixer\ DACL 25 unmute
amixer set -c 0 Digital 255 unmute
insmod /lib/modules/fd-xtensa-hifi.ko
insmod /lib/modules/fd-alsa-drv.ko
_fd-dsp &
sleep 4
fd-dsp-test &
