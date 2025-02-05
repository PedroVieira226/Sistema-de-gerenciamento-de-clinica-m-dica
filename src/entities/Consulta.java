package entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class Consulta {
    private int idConsulta;
    private Date dataDaConsulta;
    private LocalDateTime horarioDeInicio;
    private LocalDateTime duracaoDaConsulta;
    private Paciente pacienteDaConsulta;//associação de classe com a classe paiente, relação "tem um"
    private Medico medicoDaConsulta;
    private Double valorConsulta;



}
