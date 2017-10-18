SUMMARY = "Fiberdyne DSP Applications"
SECTION = "Fiberdyne DSP"
LICENSE = "CLOSED"

PR = "r0"
PV = "0.1"

SRC_URI = "file://fd-dsp-bin.tar.gz \
           file://fd-dsp.sh \
           file://fd-dsp-test.sh \
           file://fd-dsp-pulse.sh \
           file://fd-dsp.service \
           file://fd-dsp-pulse.service \
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
   
   # Install scripts
    install -d ${D}${bindir}
    install ${WORKDIR}/fd-dsp.sh ${D}/${bindir}/ 
    install ${WORKDIR}/fd-dsp-pulse.sh ${D}/${bindir}/
   
   # Install test executables and scripts
    install ${WORKDIR}/fd-dsp-test.sh ${D}/${bindir}/
    install ${WORKDIR}/fd-dsp-bin/ak4613_start ${D}/${bindir}/
   
   # Install .wav files
    install -d ${D}/usr/share
    install ${WORKDIR}/fd-dsp-bin/fd-dsp-test.wav ${D}/usr/share/
    install ${WORKDIR}/fd-dsp-bin/fd-dsp-sweep.wav ${D}/usr/share/
   
   # Install Audio player
    install ${WORKDIR}/fd-dsp-bin/xf_audioplayer  ${D}/${bindir}/
   
   # Install AGL widgets
    install -d ${D}/home/root
    install ${WORKDIR}/fd-dsp-bin/fd-dsp.wgt ${D}/home/root/
    install ${WORKDIR}/fd-dsp-bin/fd-dsp-service.wgt ${D}/home/root/
   
   # Install systemd fd-dsp.service (system-space)
    install -d ${D}/${base_libdir}/systemd/system
    install ${WORKDIR}/fd-dsp.service ${D}/${base_libdir}/systemd/system/
   
   # Install systemd fd-dsp-pulse.service (user-space)
    install -d ${D}/usr/lib/systemd/user/
    install ${WORKDIR}/fd-dsp-pulse.service ${D}/usr/lib/systemd/user

   # Create symlinks
    ln -s ${bindir}/fd-dsp.sh ${D}/${bindir}/fd-dsp
    ln -s ${bindir}/fd-dsp-test.sh ${D}/${bindir}/fd-dsp-test
    ln -s ${bindir}/fd-dsp-pulse.sh ${D}/${bindir}/fd-dsp-pulse
   
   # Create symlink to fd-dsp.service
    install -d ${D}/${sysconfdir}/systemd/system/default.target.wants
    ln -s ${base_libdir}/systemd/system/fd-dsp.service ${D}/${sysconfdir}/systemd/system/default.target.wants/
   
   # Create symlink to fd-dsp-pulse.service
    install -d ${D}/home/root/.config/systemd/user/default.target.wants/
    ln -s /usr/lib/systemd/user/fd-dsp-pulse.service ${D}/home/root/.config/systemd/user/default.target.wants
}

# Package the installed files
FILES_${PN} = " \
    ${base_libdir}/firmware/xf-rcar.fw \
    ${base_libdir}/modules/fd-xtensa-hifi.ko \
    ${base_libdir}/systemd/system/fd-dsp.service \
    ${sysconfdir}/systemd/system/default.target.wants/fd-dsp.service \
    ${bindir}/fd-dsp \
    ${bindir}/fd-dsp.sh \
    ${bindir}/fd-dsp-test \
    ${bindir}/fd-dsp-test.sh \
    ${bindir}/fd-dsp-pulse \
    ${bindir}/fd-dsp-pulse.sh \
    ${bindir}/xf_audioplayer \
    ${bindir}/ak4613_start \
    /usr/share/fd-dsp-test.wav \
    /usr/share/fd-dsp-sweep.wav \
    /usr/lib/systemd/user/fd-dsp-pulse.service \
    /home/root/.config/systemd/user/default.target.wants/fd-dsp-pulse.service \
    /home/root/fd-dsp.wgt \
    /home/root/fd-dsp-service.wgt \
    "

# Runtime dependencies
RDEPENDS_${PN} = "\
    bash \ 
    libasound \
    boost-system \
    boost-thread \
    boost-filesystem \
    qtbase \
    qtcharts \
    "
