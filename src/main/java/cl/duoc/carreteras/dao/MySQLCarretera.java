package cl.duoc.carreteras.dao;

import cl.duoc.carreteras.conexion.ConexionBD;
import cl.duoc.carreteras.model.Carretera;
import cl.duoc.carreteras.model.CarreteraConcesionada;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLCarretera implements CarreteraDAO{
    private static final String SELECT_ALL = "SELECT * FROM Carretera";
    private static final String INSERT_CC = "INSERT INTO carretera (id_tipo, codigo, numero_pistas, longitud_km, tarifa_peaje, iluminada, tag_habilitado) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_CE = "INSERT INTO carretera (id_tipo, codigo, numero_pistas, longitud_km, altitud_maxima) values (?, ?, ?, ?, ?)";


    private final CarreteraMapper mapper = new CarreteraMapper();

    @Override
    public List<Carretera> obtenerTodos() throws PersistenciaException{
        List<Carretera> carreteras = new ArrayList<>();
        String sql = SELECT_ALL;

        try (Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                carreteras.add(CarreteraMapper.mapear(rs));
            }
            return carreteras;
        } catch (SQLException e) {
            throw new PersistenciaException("ERROR AL LISTAR CARRETAS", e);
        }

    }

    @Override
    public void insertar(Carretera carretera) throws PersistenciaException{
        String sql;
        if(carretera instanceof CarreteraConcesionada){
            sql = INSERT_CC;
        }
    }

    @Override
    public void actualizar(Carretera carretera) throws PersistenciaException{
        String sql;
    }

    @Override
    public void eliminar(int id) throws PersistenciaException {

    }
}
