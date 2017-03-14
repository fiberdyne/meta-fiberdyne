
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

## IMPORTANT - The following patches DO NOT work with the AGL meta-netboot.
##             Possibly due to reserved memory overlapping

SRC_URI_append_porter = " \
	file://debug-rcar.cfg \
	file://porter_ext01.cfg \
    file://fixed_phy/0001-net-phy-breakdown-PHY_-_FEATURES-defines.patch \
	file://fixed_phy/0002-net-phy-decouple-PHY-id-and-PHY-address-in-fixed-PHY.patch \
	file://fixed_phy/0003-net-phy-extend-fixed-driver-with-fixed_phy_register.patch \
	file://fixed_phy/0004-net-of_mdio-factor-PHY-registration-from-of_mdiobus_.patch \
	file://fixed_phy/0005-net-of_mdio-use-PHY_MAX_ADDR-constant.patch \
	file://fixed_phy/0006-net-of_mdio-do-not-overwrite-PHY-interrupt-configura.patch \
	file://fixed_phy/0007-net-of_mdio-parse-max-speed-property-to-set-PHY-supp.patch \
	file://fixed_phy/0008-phylib-Add-of_phy_attach.patch \
	file://fixed_phy/0009-net-of_mdio-fix-of_set_phy_supported-after-driver-pr.patch \
	file://fixed_phy/0010-of_mdio-fix-phy-interrupt-passing.patch \
	file://fixed_phy/0011-of_mdio-Allow-the-DT-to-specify-the-phy-ID-and-avoid.patch \
	file://fixed_phy/0012-of-provide-a-binding-for-fixed-link-PHYs.patch \
	file://fixed_phy/0013-of-mdio-remove-of_phy_connect_fixed_link.patch \
	file://fixed_phy/0014-net-of_mdio-factor-out-code-to-parse-a-phy-s-reg-pro.patch \
	file://fixed_phy/0015-net-of_mdio-add-of_mdiobus_link_phydev.patch \
	file://fixed_phy/0016-net-of_mdio-don-t-store-the-length-of-a-property-if-.patch \
	file://fixed_phy/0017-net-of_mdio-use-int-type-for-address-variable.patch \
	file://fixed_phy/0018-of-of_mdio-export-symbol-of_mdiobus_link_phydev.patch \
	file://fixed_phy/0019-of-mdio-fixup-of_phy_register_fixed_link-parsing-of-.patch \
	file://fixed_phy/0020-net-fix-circular-dependency-in-of_mdio-code.patch \
	file://fixed_phy/0021-of-mdio-honor-flags-passed-to-of_phy_connect.patch \
	file://fixed_phy/0022-of-mdio-export-of_mdio_parse_addr.patch \
	file://0023-R8A7791-add-MLP-to-dtsi.patch \
	file://0024-R8A7791-add-MLP-clock.patch \
	file://0025-R8A7791-add-MLP-pin-mux-3-pin-mode-and-fix-typo.patch \
	file://avb/0001-ravb-Add-fixed-link-support.patch \
	file://avb/0002-AVB-support-Porter.patch \
	file://avb/0003-add-avb-clock-to-7791.dtsi.patch \
	file://avb/0004-add-RAVB-Renesas-support.patch \
	file://avb/0005-RAVB-add-pinctrl-porter.patch \
	file://avb/0006-sh-eth-Add-fixed-link-support.patch \
	file://porter/0004-GPIO-CPLD-gpio-extender-driver.patch \
	file://porter/0012-regmap-Implemented-default-cache-sync-operation.patch \
	file://porter/0013-regmap-cache-Don-t-attempt-to-sync-non-writeable-reg.patch \
	file://porter/0023-SPI-GPIO-get-master-fix.patch \
	file://porter/0024-SPIDEV-extend-maximum-transfer-size.patch \
	file://porter/0025-Radio-add-si468x-to-spidev.patch \
	file://porter/0099-Porter-add-separate-dts-for-ext01-extension-board.patch \
	file://porter/0100-Porter-ext01-add-dummy-regulator-to-select-48000-sou.patch \
	file://porter/0014-ASoC-Add-SOC_DOUBLE_STS-macro.patch \
	file://porter/0015-ASoC-pcm3168a-Add-binding-document-for-pcm3168a-code.patch \
	file://porter/0016-ASoC-pcm3168a-Add-driver-for-pcm3168a-codec.patch \
	file://porter/0017-ASoC-PCM3168A-add-TDM-modes.patch \
	file://porter/0018-ASoC-PCM3168A-disable-16-bit-format.patch \
	file://porter/0019-ASoC-PCM3168A-enable-on-power-on.patch \
	file://porter/0020-ASoC-PCM3168A-merge-ADC-and-DAC-to-single-DAI.patch \
	file://porter/0021-ASoC-PCM3168A-disable-PM.patch \
	file://porter/0022-ASoC-fix-simple-card-do-not-respect-device-id.patch \
	file://porter/0027-ASoC-R-Car-initial-TDM-support.patch \
	file://porter/0028-ASoC-rcar-correct-32bit-to-24-bit-sample-conv.patch \
	file://porter/0029-ASoC-R-Car-fix-debug-output.patch \
	file://porter/0030-R-Car-sound-disable-clock-hack.patch \
	file://porter/0031-ASoC-R-car-SSI-fix-SSI-slave-mode-setup-while-TDM-an.patch \
	file://porter/0032-ASoC-rsnd-care-SWSP-bit-for-TDM-non-TDM.patch \
	file://porter/0033-ASoC-R-car-SSI-fix-SSI-slave-mode-setup-while-TDM-an.patch \
	file://porter/0034-ASoC-RCar-fix-capture-in-TDM-mode.patch \
	file://adsp/ssi_debug.patch \
	file://adsp/0001-arm-porter_ADSP-support.patch \
    "
    
KERNEL_DEVICETREE_append_porter = " ${S}/arch/arm/boot/dts/r8a7791-porter-ext01.dts "
    
## This patch (meta-agl/meta-agl-bsp/meta-renesas))disables sound
## REMOVE IT!
#SRC_URI_remove = " file://disable_soc_sound.patch"


