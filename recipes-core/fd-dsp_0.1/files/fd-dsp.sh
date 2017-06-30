#!/bin/bash

## Load kernel module that loads the ADSP firmware
insmod /lib/modules/fd-xtensa-hifi.ko
sleep 1

## Initialize codecs
ak4613_start &
sleep 1
_fd-dsp &
sleep 3
fd-dsp-test &
