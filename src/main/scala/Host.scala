import chisel3._
import chisel3.util._
import Bus_Enum_Config._


class Host_IO extends Bundle{
    // for CPU
    val cpu_rvalid          = Input(Bool())
    val cpu_rready          = Output(Bool())
    val cpu_wvalid          = Input(Bool())
    val cpu_wready          = Output(Bool())
    val cpu_addr            = Input(UInt(32.W))
    val cpu_wdata           = Input(UInt(32.W))
    val cpu_rdata           = Output(UInt(32.W))

    // for Root Hub
    // package
    val bm_request_type   = Output(UInt(8.W))
    val b_request         = Output(UInt(8.W))
    val w_value           = Output(UInt(16.W))
    val w_index           = Output(UInt(16.W))
    val w_length          = Output(UInt(16.W))
    val en                = Output(Bool())

    // bus enumeration
    val has_connect         = Input(Bool())
    val bus_reset           = Output(Bool())
    val usb_speed           = Input(UInt(2.W))

}

class Host extends Module{
    val io = IO(new Host_IO)

    val s_idle :: s_connect :: s_reset :: s_detect :: s_get_speed :: s_wait :: s_enum :: s_ready :: Nil = Enum(8)
    val e_idle :: e_get_descriptor :: e_set_addr :: e_get_config :: e_set_config :: Nil = Enum(5)
    val state               = RegInit(0.U(4.W))
    val state_past          = RegInit(0.U(4.W))
    val e_state             = RegInit(0.U(3.W))

    val bm_request_type     = RegInit(0.U(8.W))
    val b_request           = RegInit(0.U(8.W))
    val w_value             = RegInit(0.U(16.W))
    val w_index             = RegInit(0.U(16.W))
    val w_length            = RegInit(0.U(16.W))
    val en                  = RegInit(false.B)

    val bus_reset           = RegInit(false.B)

    // count down
    val counter_down        = RegInit(0.U(32.W))
    val counter_set         = WireDefault(false.B)
    val counter_set_value   = RegInit(0.U(32.W))
    val counter_time_out    = counter_down === 0.U

    val usb_speed           = RegInit(0.U(1.W))
    val enum_finish         = WireDefault(false.B)

    // main FSM
    switch(state){
        is(s_idle){
            when(io.has_connect){
                state       := s_connect
            }
        }
        is(s_connect){
            state               := s_wait
            counter_set_value   := BUS_RESET_COUNT
            counter_set         := true.B
            state_past          := s_get_speed
        }
        is(s_get_speed){
            when(io.usb_speed(1)){
                state               := s_reset
                usb_speed           := io.usb_speed(0)

            }.otherwise(
                state               := s_get_speed
            )
        }
        is(s_reset){
            counter_set_value   := BUS_RESET_COUNT
            counter_set         := true.B
            state_past          := s_detect
            state               := s_wait
            bus_reset           := true.B
        }
        is(s_detect){
            when(io.has_connect){
                state       := s_enum
            }.otherwise(
                state       := s_idle
            )
        }
        is(s_enum){
            when(enum_finish){
                state       := s_ready
            }.otherwise(
                state       := s_enum
            )
        }
        is(s_wait){
            when(counter_time_out){
                state       := state_past
                counter_set := false.B
                bus_reset   := false.B
            }.otherwise(
                state       := s_wait
            )
        }

    }

    
}
