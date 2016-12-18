
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_porter = " \
	file://0001-arm-porter_ADSP-support.patch \
	file://debug-rcar.cfg \	
 	"
