# Test direct into hardware buffer sink, avoiding mixer
pcm3168_start &

while true; do
  aplay -D hw:1,0,0 -c2 -fS16_LE /usr/share/fd-dsp-test.wav
done
