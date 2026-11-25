package cl.duoc.carreteras.dao;

import cl.duoc.carreteras.model.Carretera;
import cl.duoc.carreteras.model.CarreteraConcesionada;
import cl.duoc.carreteras.model.CarreteraEstatal;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarreteraMapper {

    public static Carretera mapear(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int idTipo = rs.getInt("id_tipo");
        String codigo = rs.getString("codigo");
        int numeroPista = rs.getInt("numero_pistas");
        double longitudKm = rs.getDouble("longitud_km");

        Carretera carretera;

        if(idTipo == 1){
            double tarifaPeaje = rs.getDouble("tarifa_peaje");
            boolean iluminada = rs.getBoolean("iluminada");
            boolean tagHabilitado = rs.getBoolean("tag_habilitado");

            CarreteraConcesionada cc = new CarreteraConcesionada(
                    codigo, numeroPista, longitudKm, tarifaPeaje, iluminada
            );
            cc.setTagHabilitado(tagHabilitado);
            carretera = cc;
        }else if(idTipo == 2){
            double altitudMax = rs.getDouble("altitud_maxima");

            carretera = new CarreteraEstatal(
                    codigo, numeroPista, longitudKm, altitudMax
            );

        }else {
            throw new SQLException("Tipo de carretera desconocido en DB: id_tipo = " + idTipo);
        }

        carretera.setId(id);
        return carretera;
    }
}
