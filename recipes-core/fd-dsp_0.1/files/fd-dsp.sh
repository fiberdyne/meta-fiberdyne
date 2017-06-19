#!/bin/bash
insmod /lib/modules/fd-xtensa-hifi.ko
sleep 1
pcm3168_start &
sleep 1
_fd-dsp &
sleep 3
fd-dsp-test &
