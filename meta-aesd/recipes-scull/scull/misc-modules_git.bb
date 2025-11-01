# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit module
inherit update-rc.d

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-woytzek.git;protocol=ssh;branch=main \
           file://0001-Makefile-for-misc-modules-and-scull-only.patch \
		   file://misc-modules-start-stop \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "a6db1c46c3f433805fb9482c39b6c42d7163ea26"

S = "${WORKDIR}/git"
FILES:${PN} += "${libdir}/modules/${KERNEL_VERSION}/extra/faulty.ko"
FILES:${PN} += "${libdir}/modules/${KERNEL_VERSION}/extra/hello.ko"
FILES:${PN} += "${sysconfdir}/init.d/misc-modules-start-stop"

###inherit module

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "misc-modules-start-stop"

MODULES_INSTALL_TARGET = "all"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"
EXTRA_OEMAKE += " -C ${S} M=${S}/misc-modules"

RPROVIDES:${PN} += "kernel-module-faulty"

do_install () {
	install -d ${D}${libdir}/modules/${KERNEL_VERSION}/extra/
	install -m 0755 ${S}/misc-modules/faulty.ko ${D}${libdir}/modules/${KERNEL_VERSION}/extra/
	install -m 0755 ${S}/misc-modules/hello.ko ${D}${libdir}/modules/${KERNEL_VERSION}/extra/
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/misc-modules-start-stop ${D}${sysconfdir}/init.d/
}