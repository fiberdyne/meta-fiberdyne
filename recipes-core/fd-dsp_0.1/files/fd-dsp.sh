#!/bin/bash
insmod /lib/modules/fd-xtensa-hifi.ko
insmod /lib/modules/fd-alsa-drv.ko
sleep 1
pcm3168_start &
sleep 1
_fd-dsp &
sleep 3
fd-dsp-test &
