import chisel3._
import chisel3.util._

class NRZI extends Module {
    val io = IO(new Bundle {
        val din         = Input(UInt(1.W))
        val dout        = Output(UInt(1.W))
    })
    val data_reg       = RegInit(1.U(1.W))
    data_reg          := io.din
    io.dout           := ~(io.din ^ data_reg)
    
}