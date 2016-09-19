# Test direct into hardware buffer sink, avoiding mixer
aplay -D "hw:1,0" -c2 -fS16_LE /usr/share/fd-dsp-test.wav
./ak41_start
