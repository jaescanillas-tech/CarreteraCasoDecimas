package cl.duoc.carreteras.dao;

import cl.duoc.carreteras.conexion.ConexionBD;
import cl.duoc.carreteras.model.Carretera;
import cl.duoc.carreteras.model.CarreteraConcesionada;
import cl.duoc.carreteras.model.CarreteraEstatal;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLCarreteraDAO implements CarreteraDAO {
    private static final String SELECT_ALL = "SELECT * FROM carretera";
    private static final String INSERT_CC = "INSERT INTO carretera (id_tipo, codigo, numero_pistas, longitud_km, tarifa_peaje, iluminada, tag_habilitado) VALUES (1, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_CE = "INSERT INTO carretera (id_tipo, codigo, numero_pistas, longitud_km, altitud_maxima) values (2, ?, ?, ?, ?)";
    private static final String UPDATE_CC = "UPDATE carretera SET numero_pistas = ?, longitud_km = ?, tarifa_peaje = ?, iluminada = ?, tag_habilitado = ? WHERE id = ?";
    private static final String UPDATE_CE = "UPDATE carretera SET numero_pistas = ?, longitud_km = ?, altitud_maxima = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM carretera WHERE id = ?";

    private final CarreteraMapper mapper = new CarreteraMapper();

    @Override
    public List<Carretera> obtenerTodos() throws PersistenciaException {
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
    public void insertar(Carretera carretera) throws PersistenciaException {
        String sql;
        if (carretera instanceof CarreteraConcesionada) {
            sql = INSERT_CC;
        } else {
            sql = INSERT_CE;
        }
        try (
                Connection con = ConexionBD.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setString(1, carretera.getCodigo());
            ps.setInt(2, carretera.getNumeroPistas());
            ps.setDouble(3, carretera.getLongitudKm());
            if (carretera instanceof CarreteraConcesionada) {
                CarreteraConcesionada cc = (CarreteraConcesionada) carretera;
                ps.setDouble(4, cc.getTarifaPeaje());
                ps.setBoolean(5, cc.isIluminada());
                ps.setBoolean(6, cc.isTagHabilitado());
            } else {
                CarreteraEstatal ce = (CarreteraEstatal) carretera;
                ps.setDouble(4, ce.getAltitudMaxima());
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizar carretera: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Carretera carretera) throws PersistenciaException {
        String sql = (carretera instanceof CarreteraConcesionada) ? UPDATE_CC : UPDATE_CE;
        try (
                Connection con = ConexionBD.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setString(1, carretera.getCodigo());
            ps.setInt(2, carretera.getNumeroPistas());
            ps.setDouble(3, carretera.getLongitudKm());
            if (carretera instanceof CarreteraConcesionada) {
                CarreteraConcesionada cc = (CarreteraConcesionada) carretera;
                ps.setDouble(4, cc.getTarifaPeaje());
                ps.setBoolean(5, cc.isIluminada());
                ps.setBoolean(6, cc.isTagHabilitado());
            } else {
                CarreteraEstatal ce = (CarreteraEstatal) carretera;
                ps.setDouble(4, ce.getAltitudMaxima());
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Error al insertar carretera: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws PersistenciaException {
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE);
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Error al eliminar carretera: " + e.getMessage(), e);
        }
    }

    public void eliminar(Carretera carretera) throws PersistenciaException {
        try(Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(DELETE);
        ){
            ps.setInt(1, carretera.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Error al eliminar participante " + e.getMessage(), e);
        }
    }
}
