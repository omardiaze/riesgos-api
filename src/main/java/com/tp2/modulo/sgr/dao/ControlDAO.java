package com.tp2.modulo.sgr.dao;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tp2.modulo.sgr.database.ConnectionJDBC;
import com.tp2.modulo.sgr.model.Control;

public class ControlDAO {

	ConnectionJDBC jdbc = new ConnectionJDBC();
	
	public ArrayList<Control> getControles() {
		
		ArrayList<Control> listaControles = new ArrayList<Control>();
		
		try (CallableStatement cs = jdbc.getConnection().prepareCall("{call INDRASS_Controles()}");) {
			
			boolean hadResults = cs.execute();
			
			System.out.println("Stored procedure called successfully!");
			
			while (hadResults) {
				ResultSet resultSet = cs.getResultSet();
				
				while (resultSet.next()) {
					Control control = new Control();
					
					control.setIdControl(resultSet.getInt("cod_Control"));
					control.setDescripcion(resultSet.getString("tx_descripcion"));
					control.setTipo(resultSet.getInt("tx_tipo"));
					control.setResponsable(resultSet.getString("tx_responsable"));
					control.setEstadoImplementacion(resultSet.getInt("tx_estadoImplemtacion"));
					control.setEquipoResponsable(resultSet.getString("tx_equipoResponsable"));
					control.setFechaImplementacion(resultSet.getDate("fe_implementacion"));
					control.setCosto(resultSet.getDouble("tx_costo"));
					control.setIdRiesgo(resultSet.getInt("cod_riesgo"));
					
					listaControles.add(control);
				}
				
				hadResults = cs.getMoreResults();
			}
			
			cs.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				jdbc.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return listaControles;
	}
	
public Control getControl(int idControl) {
		
	Control control = new Control();
	
	try (CallableStatement cs = jdbc.getConnection().prepareCall("{call INDRASS_Control(?)}");) {
		cs.setInt(1, idControl);
		boolean hadResults = cs.execute();
		
		System.out.println("Stored procedure called successfully!");
		
		while (hadResults) {
			ResultSet resultSet = cs.getResultSet();
			
			while (resultSet.next()) {					
				control.setIdControl(resultSet.getInt("cod_Control"));
				control.setDescripcion(resultSet.getString("tx_descripcion"));
				control.setTipo(resultSet.getInt("tx_tipo"));
				control.setResponsable(resultSet.getString("tx_responsable"));
				control.setEstadoImplementacion(resultSet.getInt("tx_estadoImplemtacion"));
				control.setEquipoResponsable(resultSet.getString("tx_equipoResponsable"));
				control.setFechaImplementacion(resultSet.getDate("fe_implementacion"));
				control.setCosto(resultSet.getDouble("tx_costo"));
				control.setIdRiesgo(resultSet.getInt("cod_riesgo"));
			}
			
			hadResults = cs.getMoreResults();
		}
		
		cs.close();
	} catch (SQLException sqle) {
		sqle.printStackTrace();
	} finally {
		try {
			jdbc.getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	return control;
	}
	
	public boolean registrarControl(Control control) {
		
		boolean respuesta = false;
		
		java.util.Date uDate = control.getFechaImplementacion();		
		
		try (CallableStatement cs = jdbc.getConnection().prepareCall("{call INDRASI_Control(?,?,?,?,?,?,?,?)}");) {
			
			cs.setString(1, control.getDescripcion());
			cs.setInt(2, control.getTipo());
			cs.setString(3, control.getResponsable());
			cs.setInt(4, control.getEstadoImplementacion());
			cs.setString(5, control.getEquipoResponsable());
			cs.setDate(6, new java.sql.Date(uDate.getTime()));
			cs.setDouble(7, control.getCosto());
			cs.setInt(8, control.getIdRiesgo());
			cs.execute();
			System.out.println("Stored procedure called successfully!");
			cs.close();
			respuesta = true;
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				jdbc.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return respuesta;
	}
	
	public boolean actualizarControl(Control control) {
		
		boolean respuesta = false;
		java.util.Date uDate = control.getFechaImplementacion();
		
		try (CallableStatement cs = jdbc.getConnection().prepareCall("{call INDRASU_Control(?,?,?,?,?,?,?,?,?)}");) {
			cs.setInt(1, control.getIdControl());
			cs.setString(2, control.getDescripcion());
			cs.setInt(3, control.getTipo());
			cs.setString(4, control.getResponsable());
			cs.setInt(5, control.getEstadoImplementacion());
			cs.setString(6, control.getEquipoResponsable());
			cs.setDate(7, new java.sql.Date(uDate.getTime()));
			cs.setDouble(8, control.getCosto());
			cs.setInt(9, control.getIdRiesgo());
			cs.execute();
			System.out.println("Stored procedure called successfully!");
			cs.close();
			respuesta = true;
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				jdbc.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return respuesta;
	}
	
	public boolean eliminarControl(int idControl) {
		
		boolean respuesta = false;
		
		try (CallableStatement cs = jdbc.getConnection().prepareCall("{call INDRASD_Control(?)}");) {
			cs.setInt(1, idControl);
			cs.execute();
			System.out.println("Stored procedure called successfully!");
			cs.close();
			respuesta = true;
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				jdbc.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return respuesta;
	}
}
