SUMMARY = "Fiberdyne DSP Applications"
SECTION = "Fiberdyne DSP"
LICENSE = "CLOSED"

PR = "r0"
PV = "0.1"

SRC_URI = "file://fd-dsp.tar.gz"

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
    install ${WORKDIR}/fd-dsp/${bindir}/fd-dsp.sh ${D}/${bindir}/ 
    install ${WORKDIR}/fd-dsp/${bindir}/_fd-dsp ${D}/${bindir}/
    install ${WORKDIR}/fd-dsp/${bindir}/fd-dsp-ui.sh ${D}/${bindir}/
    install ${WORKDIR}/fd-dsp/${bindir}/_fd-dsp-ui ${D}/${bindir}/
   # Install test executables and scripts
    install ${WORKDIR}/fd-dsp/${bindir}/fd-dsp-test.sh ${D}/${bindir}/
    install ${WORKDIR}/fd-dsp/${bindir}/ak41_start ${D}/${bindir}/
   # Install test .wav files
    install -d ${D}/usr/share
    install ${WORKDIR}/fd-dsp/usr/share/fd-dsp-test.wav ${D}/usr/share/
   # Install .asoundrc for alsamixer
    install -d ${D}/home/root
    install ${WORKDIR}/fd-dsp/home/root/.asoundrc ${D}/home/root/
   # Install chirp
    install ${WORKDIR}/fd-dsp/home/root/chirp.wav ${D}/home/root/
   # Create symlinks
    ln -s ${bindir}/fd-dsp.sh ${D}/${bindir}/fd-dsp
    ln -s ${bindir}/fd-dsp-ui.sh ${D}/${bindir}/fd-dsp-ui
    ln -s ${bindir}/fd-dsp-test.sh ${D}/${bindir}/fd-dsp-test
}

# Package the installed files
FILES_${PN} = " \
    ${base_libdir}/firmware/xf-m2.fw \
    ${base_libdir}/modules/fd-xtensa-hifi.ko \
    ${base_libdir}/modules/fd-alsa-drv.ko \
    ${sysconfdir}/dbus-1/system-local.conf \
    ${bindir}/fd-dsp \
    ${bindir}/fd-dsp.sh \
    ${bindir}/_fd-dsp \
    ${bindir}/fd-dsp-ui \
    ${bindir}/fd-dsp-ui.sh \
    ${bindir}/_fd-dsp-ui \
    ${bindir}/fd-dsp-test \
    ${bindir}/fd-dsp-test.sh \
    ${bindir}/ak41_start \
    /usr/share/fd-dsp-test.wav \
    /home/root/.asoundrc \
    /home/root/chirp.wav \
    "

# Runtime dependencies
RDEPENDS_${PN} = "\
    libasound \
    boost-system \
    boost-thread \
    boost-filesystem \
    dbus-lib \
    dbus-glib \
    qtbase \
    "
