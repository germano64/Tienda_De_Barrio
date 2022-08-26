/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import persistencia.ConexionBD;

/**
 *
 * @author fido
 */
public class Producto {

    private int id;
    private String nombre;
    private int valor_base;
    private String temperatura;
    private double costo;

    public Producto() {
    }

    public Producto(String nombre, int valor_base, String temperatura, double costo) {
        this.nombre = nombre;
        this.valor_base = valor_base;
        this.temperatura = temperatura;
        this.costo = costo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getValor_base() {
        return valor_base;
    }

    public void setValor_base(int valor_base) {
        this.valor_base = valor_base;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", nombre=" + nombre + ", valor_base=" + valor_base + ", temperatura=" + temperatura + ", costo=" + costo + '}';
    }

    public Producto getProducto(int id) {
        ConexionBD conexion = new ConexionBD();
        String sql = "select * from productos WHERE id="+id+";";
        try {
            ResultSet rs = conexion.consultarBD(sql);
            if (rs.next()) {
                this.id = rs.getInt("id");
                this.valor_base = rs.getInt("valor_base");
                this.nombre = rs.getString("nombre");
                this.temperatura = rs.getString("temperatura");
                this.costo = rs.getDouble("costo");
            }
        } catch (SQLException ex) {
            System.out.println("Error" + ex.getMessage());
        } finally {
            conexion.cerrarConexion();
        }
        return this;
    }

    public List<Producto> listarProductos() {
        List<Producto> listaProductos = new ArrayList<>();
        ConexionBD conexion = new ConexionBD();
        String sql = "select * from productos;";
        try {
            ResultSet rs = conexion.consultarBD(sql);
            Producto p;
            while (rs.next()) {
                p = new Producto();
                p.setId(rs.getInt("id"));
                p.setValor_base(rs.getInt("valor_base"));
                p.setNombre(rs.getString("nombre"));
                p.setTemperatura(rs.getString("temperatura"));
                p.setCosto(rs.getDouble("costo"));
                listaProductos.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Error" + ex.getMessage());
        } finally {
            conexion.cerrarConexion();
        }
        return listaProductos;
    }

    public boolean guardarProducto() {
        ConexionBD conexion = new ConexionBD();
        String sql = "INSERT INTO productos(id,nombre,temperatura,valor_base,costo)"
                + "VALUES("+this.id+",'" + this.nombre + "','" + this.temperatura + "'," + this.valor_base + "," + this.costo + ");";
        if (conexion.setAutoCommitBD(false)) {
            if (conexion.insertarBD(sql)) {
                conexion.commitBD();
                conexion.cerrarConexion();
                return true;
            } else {
                conexion.rollbackBD();
                conexion.cerrarConexion();
                return false;
            }
        } else {
            conexion.cerrarConexion();
            return false;
        }
    }

    public boolean actualizarProducto() {
        ConexionBD conexion = new ConexionBD();
        String sql = "UPDATE productos SET nombre='"
                + this.nombre + "',temperatura='" + this.temperatura
                + "',valor_base=" + this.valor_base + ",costo="
                + this.costo + " WHERE id=" + this.id + ";";
        if (conexion.setAutoCommitBD(false)) {
            if (conexion.actualizarBD(sql)) {
                conexion.commitBD();
                conexion.cerrarConexion();
                return true;
            } else {
                conexion.rollbackBD();
                conexion.cerrarConexion();
                return false;
            }
        } else {
            conexion.cerrarConexion();
            return false;
        }
    }

    public boolean eliminarProducto() {
        ConexionBD conexion = new ConexionBD();
        String sql = "DELETE FROM productos WHERE id=" + this.id + ";";
        if (conexion.setAutoCommitBD(false)) {
            if (conexion.actualizarBD(sql)) {
                conexion.commitBD();
                conexion.cerrarConexion();
                return true;
            } else {
                conexion.rollbackBD();
                conexion.cerrarConexion();
                return false;
            }
        } else {
            conexion.cerrarConexion();
            return false;
        }
    }

}
