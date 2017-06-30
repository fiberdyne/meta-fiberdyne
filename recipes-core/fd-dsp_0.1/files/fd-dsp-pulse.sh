#!/bin/bash

## Configure PulseAudio mono sinks
pactl load-module module-alsa-sink device=hw:1,0,1 channels=1
pactl load-module module-alsa-sink device=hw:1,0,2 channels=1

