DESCRIPTION = "Fiberdyne DSP library"
LICENSE = "Copyright Fiberdyne Systems Ptv Ltd"
# dummy
LIC_FILES_CHKSUM = "file://README;md5=13a41cb73f4e89cc25f6d30a672cd576"

#DEPENDS = ""

inherit module

PR = "r0"
PV = "0.1"

S = "${WORKDIR}"

SRCREV = "${AUTOREV}"
SRC_URI = "git://git@alpha.fiberdyne.com.au:20122/internal/ampoem.git;protocol=ssh;branch=renesas_port \
           file://Makefile \
           file://README \
          "

# inherit autotools pkgconfig
#export INSTALL_MOD_DIR="kernel/linux-fusion-modules"
#export KERNEL_SRC="${STAGING_KERNEL_DIR}"

#do_install_append() {
#    install -d ${D}/usr/include/linux
#    install -m 0644 ${S}/linux/include/linux/fusion.h ${D}/usr/include/linux
#}

#FILES_${PN}-headers = "/usr/include/linux"
