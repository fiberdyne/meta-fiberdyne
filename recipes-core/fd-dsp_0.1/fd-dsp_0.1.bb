SUMMARY = "Fiberdyne DSP Applications"
SECTION = "Fiberdyne DSP"
LICENSE = "CLOSED"

PR = "r0"
PV = "0.1"

SRC_URI = "file://fd-dsp-bin.tar.gz \
           file://system-local.conf \
           file://fd-dsp.sh \
           file://fd-dsp-ui.sh \
           file://fd-dsp-test.sh \
           file://fd-dsp.service \
           "

# Skip configure and compilation
do_configure[noexec] = "1"
do_compile[noexec] = "1"


# Install files
do_install () {
   # Install firmware
    install -d ${D}${base_libdir}/firmware/
    install ${WORKDIR}/fd-dsp-bin/xf-rcar.fw ${D}${base_libdir}/firmware/
   # Install kernel modules
    install -d ${D}${base_libdir}/modules/
    install ${WORKDIR}/fd-dsp-bin/fd-xtensa-hifi.ko ${D}${base_libdir}/modules/
   # Install DBus conf
    install -d ${D}${sysconfdir}/dbus-1/
    install ${WORKDIR}/system-local.conf ${D}/${sysconfdir}/dbus-1/
   # Install executables and scripts
    install -d ${D}${bindir}
    install ${WORKDIR}/fd-dsp.sh ${D}/${bindir}/ 
    install ${WORKDIR}/fd-dsp-bin/_fd-dsp ${D}/${bindir}/
    install ${WORKDIR}/fd-dsp-ui.sh ${D}/${bindir}/
    install ${WORKDIR}/fd-dsp-bin/_fd-dsp-ui ${D}/${bindir}/
   # Install test executables and scripts
    install ${WORKDIR}/fd-dsp-test.sh ${D}/${bindir}/
    install ${WORKDIR}/fd-dsp-bin/ak4613_start ${D}/${bindir}/
   # Install .wav files
    install -d ${D}/usr/share
    install ${WORKDIR}/fd-dsp-bin/fd-dsp-test.wav ${D}/usr/share/
    install ${WORKDIR}/fd-dsp-bin/fd-dsp-sweep.wav ${D}/usr/share/
   # Install Audio player
    install ${WORKDIR}/fd-dsp-bin/xf_audioplayer  ${D}/${bindir}/
   # Install afm fd-dsp-ui.wgt
    install -d ${D}/home/root
    install ${WORKDIR}/fd-dsp-bin/fd-dsp-ui.wgt ${D}/home/root/
   # Install systemd fd-dsp.service
    install -d ${D}/${base_libdir}/systemd/system
    install ${WORKDIR}/fd-dsp.service ${D}/${base_libdir}/systemd/system/
   # Create symlinks
    ln -s ${bindir}/fd-dsp.sh ${D}/${bindir}/fd-dsp
    ln -s ${bindir}/fd-dsp-ui.sh ${D}/${bindir}/fd-dsp-ui
    ln -s ${bindir}/fd-dsp-test.sh ${D}/${bindir}/fd-dsp-test
   # Create symlinks to fd-dsp.service
    install -d ${D}/${sysconfdir}/systemd/system/default.target.wants
    ln -s ${base_libdir}/systemd/system/fd-dsp.service ${D}/${sysconfdir}/systemd/system/default.target.wants/
}

# Package the installed files
FILES_${PN} = " \
    ${base_libdir}/firmware/xf-rcar.fw \
    ${base_libdir}/modules/fd-xtensa-hifi.ko \
    ${base_libdir}/systemd/system/fd-dsp.service \
    ${sysconfdir}/systemd/system/default.target.wants/fd-dsp.service \
    ${sysconfdir}/dbus-1/system-local.conf \
    ${bindir}/fd-dsp \
    ${bindir}/fd-dsp.sh \
    ${bindir}/_fd-dsp \
    ${bindir}/fd-dsp-ui \
    ${bindir}/fd-dsp-ui.sh \
    ${bindir}/_fd-dsp-ui \
    ${bindir}/fd-dsp-test \
    ${bindir}/fd-dsp-test.sh \
    ${bindir}/xf_audioplayer \
    ${bindir}/ak4613_start \
    /usr/share/fd-dsp-test.wav \
    /usr/share/fd-dsp-sweep.wav \
    /home/root/fd-dsp-ui.wgt \
    "

# Runtime dependencies
RDEPENDS_${PN} = "\
    bash \ 
    libasound \
    boost-system \
    boost-thread \
    boost-filesystem \
    dbus-lib \
    dbus-glib \
    qtbase \
    "
