package Dao;

import Model.Alerta;
import Utils.ConDB;
import Utils.ConnectionUtil;
import Utils.Dialog;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class alertaDao extends Alerta implements Dao {

    Connection con;

    enum queries {
        INSERT("INSERT INTO imp.p856_alertas (email,xalmacen_id,xarticulo_id,xcantidad,xcondicion,xempresa_id,xperiodicidad,xtipo_alerta,xusuario_id,xalerta_id) VALUES (?,?,?,?,?,?,?,?,?,?)"),
        ALL("select email,xalmacen_id,xarticulo_id,xcantidad,xcondicion,xempresa_id,xperiodicidad,xtipo_alerta,xusuario_id,xalerta_id from imp.p856_alertas"),
        GETBYID("SELECT email,xalerta_id,xalmacen_id,xarticulo_id,xcantidad,xcondicion,xempresa_id,xperiodicidad,xtipo_alerta,xusuario_id FROM imp.p856_alertas WHERE xalerta_id=?"),
        FINDBYID("SELECT * FROM imp.p856_alertas WHERE id IN "), //<-- ojo con esta, hay formas más elegantes
        //      UPDATE("UPDATE imp.p856_alertas SET nombre = ? WHERE id = ?"),
        REMOVE("DELETE FROM imp.p856_alertas WHERE xalerta_id=?");
        private final String q;

        queries(String q) {
            this.q = q;
        }

        public String getQ() {
            return this.q;
        }
    }

    ResultSet rs;


    @Override
    public void save() throws SQLException, ClassNotFoundException {
        queries q = null;
        List<Object> params = new ArrayList<>();
        params.add(this.getEmailTo());
        params.add(this.getAlmacenID());
        params.add(this.getArticulo_id());
        params.add(this.getCantidad());
        params.add(this.getCondicion());
        params.add(this.getEmpresaID());
        params.add(this.getPeriodicidad());
        params.add(this.getTipoAlerta());
        params.add(this.getUsaurio_id());
        con = ConDB.getCon();


        int increment = 0;

        try {

            //Consulta que carga las referencias de cada fila
            String pesos = "SELECT count(*) from imp.p856_alertas";
            Statement stmt = ConDB.getCon().createStatement();
            rs = stmt.executeQuery(pesos);

            //INICIO DE INSERCIÓN FILAS
            while (rs.next()) {
                increment = rs.getInt(1);


            }
        } catch (SQLException e) {

        }


        increment =increment +2;

        if (getId() == "-1") {
            q = queries.INSERT;
            //  params.add(Math.random());

            params.add(increment);

        } else {
            //  q = queries.UPDATE;
            //  params.add(this.id);
        }


        try {
            con = ConDB.getCon();
            //Comienza transacción
            con.setAutoCommit(false);

            int rs = ConnectionUtil.execUpdate(con, q.getQ(), params, (q == queries.INSERT));
            if (q == alertaDao.queries.INSERT) {
                this.id = "" + rs;
            }

            //Fin de la transacción
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException | ClassNotFoundException ex) {
            Dialog.showError("ERROR", "Error guardando la alerta", ex.toString());
        }
    }

    public alertaDao(Alerta j) {
        super(j.getId(), j.getEmailTo(), j.getAlmacenID(), j.getArticulo_id(), j.getCantidad(), j.getCondicion(), j.getEmpresaID(), j.getPeriodicidad(), j.getTipoAlerta(), j.getUsaurio_id());
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    private boolean persist;

    public alertaDao(String id, String emailTo, String almacenID, String articulo_id, double cantidad, String condicion, String empresaID, String periodicidad, String tipoAlerta, String usaurio_id) {
        super(id, emailTo, almacenID, articulo_id, cantidad, condicion, empresaID, periodicidad, tipoAlerta, usaurio_id);
    }

    public alertaDao() {
        super();
        try {
            con = ConDB.getCon();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(alertaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        persist = false;
    }

   /*
    //DAO
    public jugadorDao(Jugador j) {
        this(j.getId(), j.getNombre());
    }*/

    /*public alertaDao(int i) {
        this();
        List<Object> params = new ArrayList<>();
        params.add(i);
        try {
            ResultSet rs = ConnectionUtil.execQuery(con, queries.GETBYID.getQ(), params);

            if (rs != null) {

                while (rs.next()) {
                    Alerta c = instanceBuilder(rs);
                    this.id = c.getId();
                    this.nombre = c.getNombre();

                }

            }
        } catch (SQLException ex) {
            Dialog.showError("ERRPR", "Error cargando la alerta", ex.toString());
        }
    }*/

    public void persist() {
        this.persist = true;
    }

    public void detach() {
        this.persist = false;
    }

    // UTILS for JUGADOR DAO
    public static Alerta instanceBuilder(ResultSet rs) throws SQLException {
        //ojo rs.getMetaData()
        Alerta c = new Alerta();
        if (rs != null) {
            c.setEmailTo(rs.getString(1));
            c.setAlmacenID(rs.getString(2));
            c.setArticulo_id(rs.getString(3));
            c.setCantidad(rs.getDouble(4));
            c.setCondicion(rs.getString(5));
            c.setEmpresaID(rs.getString(6));
            c.setPeriodicidad(rs.getString(7));
            c.setTipoAlerta(rs.getString(8));
            c.setUsaurio_id(rs.getString(9));
            c.setId(rs.getString(10));

        }
        return c;
    }


    public static List<Alerta> getTodasAlertas(Connection con) {
        List<Alerta> result = new ArrayList<>();
        try {
            con=ConDB.getCon();
            ResultSet rs = ConnectionUtil.execQuery(con, queries.ALL.getQ(), null);
            if (rs != null) {
                while (rs.next()) {
                    Alerta n = alertaDao.instanceBuilder(rs);
                    result.add(n);
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Dialog.showError("ERRPR", "Error cargando la alerta", ex.toString());
        }
        return result;
    }


    public static List<Alerta> getById(Connection con, List<Integer> ids) {
        List<Alerta> result = new ArrayList<>();
        try {
            List<String> newList = new ArrayList<String>(ids.size());
            for (Integer myInt : ids) {
                newList.add(String.valueOf(myInt));
            }
            String queryTotal = queries.FINDBYID.getQ() + "(" + String.join(",", newList) + ");";

            ResultSet rs = ConnectionUtil.execQuery(con, queryTotal, null);
            if (rs != null) {
                while (rs.next()) {
                    Alerta n = alertaDao.instanceBuilder(rs);
                    result.add(n);
                }
            }
        } catch (SQLException ex) {
            Dialog.showError("ERRPR", "Error cargando la alerta", ex.toString());
        }
        return result;
    }
}

