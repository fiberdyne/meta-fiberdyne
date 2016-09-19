SUMMARY = "Fiberdyne DSP Applications"
SECTION = "Fiberdyne DSP"
LICENSE = "CLOSED"

PR = "r0"
PV = "0.1"

SRC_URI = "file://fd-dsp.tar.gz"

#inherit bin_package
do_configure[noexec] = "1"
do_compile[noexec] = "1"

# Install files
do_install () {
   # Install firmware
    install -d ${D}${base_libdir}/firmware/
    install ${WORKDIR}/fd-dsp/lib/firmware/xf-m2.fw ${D}${base_libdir}/firmware/
   # Install kernel modules
    install -d ${D}${base_libdir}/modules/
    install ${WORKDIR}/fd-dsp/lib/modules/fd-xtensa-hifi.ko ${D}${base_libdir}/modules/
    install ${WORKDIR}/fd-dsp/lib/modules/fd-alsa-drv.ko ${D}${base_libdir}/modules/
   # Install DBus conf
    install -d ${D}${sysconfdir}/dbus-1/
    install ${WORKDIR}/fd-dsp/etc/dbus-1/system-local.conf ${D}/${sysconfdir}/dbus-1/
   # Install executables and scripts
    install -d ${D}${bindir}
    install -m 755 ${WORKDIR}/fd-dsp/usr/bin/fd-dsp.sh ${D}/${bindir}/ 
    install -m 755 ${WORKDIR}/fd-dsp/usr/bin/fd-dsp ${D}/${bindir}/
    install -m 755 ${WORKDIR}/fd-dsp/usr/bin/fd-dsp-ui.sh ${D}/${bindir}/
    install -m 755 ${WORKDIR}/fd-dsp/usr/bin/fd-dsp-ui ${D}/${bindir}/
   # Install test executables and scripts
    install -m 755 ${WORKDIR}/fd-dsp/usr/bin/fd-dsp-test.sh ${D}/${bindir}/
    install -m 755 ${WORKDIR}/fd-dsp/usr/bin/ak41_start ${D}/${bindir}/
   # Install test .wav files
    install -d ${D}/usr/share
    install ${WORKDIR}/fd-dsp/usr/share/fd-dsp-test.wav ${D}/usr/share/
}

# Package the installed files
FILES_${PN} = " \
    ${base_libdir}/firmware/xf-m2.fw \
    ${base_libdir}/modules/fd-xtensa-hifi.ko \
    ${base_libdir}/modules/fd-alsa-drv.ko \
    ${sysconfdir}/dbus-1/system-local.conf \
    ${bindir}/fd-dsp.sh \
    ${bindir}/fd-dsp \
    ${bindir}/fd-dsp-ui.sh \
    ${bindir}/fd-dsp-ui \
    ${bindir}/fd-dsp-test.sh \
    ${bindir}/ak41_start \
    /usr/share/fd-dsp-test.wav \
    "
