FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"


COMPATIBLE_MACHINE_esdc2012 = “esdc2012"
KMACHINE_esdc2012 = "yocto/standard/crownbay"


KERNEL_FEATURES_append_esdc2012 += " cfg/smp.scc"

SRCREV_machine_pn-linux-yocto_esdc2012 ?= "63c65842a3a74e4b"
SRCREV_meta_pn-linux-yocto_esdc2012 ?= "59314a3523e3607"
SRC_URI += "file://libdsp.cfg"

