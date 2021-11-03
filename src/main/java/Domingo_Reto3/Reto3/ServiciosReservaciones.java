    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domingo_Reto3.Reto3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alejandra
 */
@Service
public class ServiciosReservaciones {
    @Autowired
    private RepositorioReservaciones metodosCrud;

    public List<Reservaciones> getAll(){
        return metodosCrud.getAll();
    }

    public Optional<Reservaciones> getReservacion(int reservationesIdId) {
        return metodosCrud.getReservacion(reservationesIdId);
    }
    public Reservaciones save(Reservaciones reservationes){
        if(reservationes.getIdReservation()==null){
            return metodosCrud.save(reservationes);
        }else{
            Optional<Reservaciones> e= metodosCrud.getReservacion(reservationes.getIdReservation());
            if(e.isEmpty()){
                return metodosCrud.save(reservationes);
            }else{
                return reservationes;
            }
        }
    }
    public Reservaciones update(Reservaciones reservaciones){
        if(reservaciones.getIdReservation()!=null){
            Optional<Reservaciones> e= metodosCrud.getReservacion(reservaciones.getIdReservation());
            if(!e.isEmpty()){

                if(reservaciones.getStartDate()!=null){
                    e.get().setStartDate(reservaciones.getStartDate());
                }
                if(reservaciones.getDevolutionDate()!=null){
                    e.get().setDevolutionDate(reservaciones.getDevolutionDate());
                }
                if(reservaciones.getStatus()!=null){
                    e.get().setStatus(reservaciones.getStatus());
                }
                metodosCrud.save(e.get());
                return e.get();
            }else{
                return reservaciones;
            }
        }else{
            return reservaciones;
        }
    }
    public boolean deleteReservation(int reservationId) {
        Boolean aBoolean = getReservacion(reservationId).map(reservationes -> {
            metodosCrud.delete(reservationes);
            return true;
        }).orElse(false);
        return aBoolean;
    }

    public StatusReservas getReservationsStatusReport(){
        List<Reservaciones>completed=metodosCrud.getReservationByStatus("completed");
        List<Reservaciones>cancelled=metodosCrud.getReservationByStatus("cancelled");
        return new StatusReservas(completed.size(), cancelled.size());
    }

    public List<Reservaciones> getReservationPeriod(String dateA, String dateB){
        SimpleDateFormat parser=new SimpleDateFormat("yyyy-MM-dd");
        Date aDate= new Date();
        Date bDate= new Date();

        try {
            aDate = parser.parse(dateA);
            bDate = parser.parse(dateB);
        }catch(ParseException evt){
            evt.printStackTrace();
        }
        if(aDate.before(bDate)){
            return metodosCrud.getReservationPeriod(aDate, bDate);
        }else{
            return new ArrayList<>();
        }

    }

    public List<ContadorClientes> getTopClients(){
        return metodosCrud.getTopClients();
    }
}
