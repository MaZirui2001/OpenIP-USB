import chisel3._
import chisel3.util._

object Standard_Device_bRequest{
    val GET_STATUS          = 0x00.U(8.W)
    val CLEAR_FEATURE       = 0x01.U(8.W)
    val SET_FEATURE         = 0x03.U(8.W)
    val SET_ADDRESS         = 0x05.U(8.W)
    val GET_DESCRIPTOR      = 0x06.U(8.W)
    val SET_DESCRIPTOR      = 0x07.U(8.W)
    val GET_CONFIGURATION   = 0x08.U(8.W)
    val SET_CONFIGURATION   = 0x09.U(8.W)
    val GET_INTERFACE       = 0x0A.U(8.W)
    val SET_INTERFACE       = 0x0B.U(8.W)
    val SYNCH_FRAME         = 0x0C.U(8.W)
}

object Bus_Enum_Config{
    val CLOCK_PERIOD            = 10.U(32.W)
    val BUS_RESET_DELAY         = 200000000.U(32.W)
    val BUS_RESET_COUNT         = BUS_RESET_DELAY / CLOCK_PERIOD
    val BUS_RESET_TIME          = 15000000.U(32.W)
    val BUS_RESET_TIME_COUNT    = BUS_RESET_TIME / CLOCK_PERIOD

}
