import chisel3._
import chisel3.util._
object USB_Time_Config{
    val CLK_TIME = 10
    val CLK_FREQ = 100_000000
    val RESET_TIME = 1000_0000
    val RESUME_TIME = 2000_0000

}
object USB_Signal_Config{
    val LOW_SPEED   = 0.U(2.W)
    val FULL_SPEED  = 1.U(2.W)
    val LOW_SPEED_VAL  = USB_Time_Config.CLK_FREQ.U / 1500000.U(32.W) 
    val FULL_SPEED_VAL = USB_Time_Config.CLK_FREQ.U / 12000000.U(32.W)
    val LOW_J       = 1.U(2.W)
    val LOW_K       = 2.U(2.W)
    val LOW_SE0     = 0.U(2.W)
    val LOW_IDLE    = LOW_J
    val FULL_J      = 2.U(2.W)
    val FULL_K      = 1.U(2.W)
    val FULL_SE0    = 0.U(2.W)
    val FULL_IDLE   = FULL_J
}
