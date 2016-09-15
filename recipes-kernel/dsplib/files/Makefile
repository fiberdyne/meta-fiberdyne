obj-m := fddsp.o
SRC := $(shell pwd)/git/src/dsp/portable/linux/renesas/module
fddsp-y :=dsp_drv_kmod.o
all:
	$(MAKE) -C $(KERNEL_SRC) M=$(SRC)

modules_install:
	$(MAKE) -C $(KERNEL_SRC) M=$(SRC) modules_install

clean:
	rm -f *.o *~ core .depend .*.cmd *.ko *.mod.c
	rm -f Module.markers Module.symvers modules.order
	rm -rf .tmp_versions Modules.symvers
