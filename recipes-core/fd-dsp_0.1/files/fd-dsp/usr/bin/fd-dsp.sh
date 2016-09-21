amixer set -c 0 Headphone\ Enable 25 unmute
amixer set -c 0 LINEOUT\ Mixer\ DACL 25 unmute
insmod /lib/modules/fd-xtensa-hifi.ko
insmod /lib/modules/fd-alsa-drv.ko
_fd-dsp
