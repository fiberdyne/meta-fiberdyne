
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

  
SRC_URI_append_m3ulcb = " \
        file://m3ulcb.cfg \
	file://adsp/r8a7796_enable_adsp.patch  \
        file://adsp/r8a7796_adsp_reservemem.patch \
    "
    

