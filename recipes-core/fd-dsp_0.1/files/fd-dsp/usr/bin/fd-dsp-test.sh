# Test direct into hardware buffer sink, avoiding mixer
ak41_start &
aplay -Dplug:sinkStereo -c2 -fS16_LE /usr/share/fd-dsp-test.wav
