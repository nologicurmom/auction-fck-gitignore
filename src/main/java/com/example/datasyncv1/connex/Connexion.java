package com.example.datasyncv1.connex;
import java.sql.*;

public class Connexion
{
    Connection con;
    public Statement stat;
    ResultSet res;

    protected static String DB = "railway";
    protected static String Username = "postgres";
    protected static String Password = "2HxL1J7BJORnaDsau1wQ";

    public Connexion(String req)
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            this.con = DriverManager.getConnection("jdbc:postgresql://containers-us-west-175.railway.app:6661/"+DB, Username, Password);
            this.stat= this.con.createStatement();
//  			this.res=stat.executeQuery(req);
            stat.execute(req);
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public Connexion()
    {
        try {
            Class.forName("org.postgresql.Driver");
            this.con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+DB, Username, Password);
        } catch (Exception e) {
        } finally {
        }
    }


    public Connexion(String req,String ide)
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            this.con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+DB, Username, Password);
            this.stat= this.con.createStatement();
            this.res=stat.executeQuery(req);
            //	stat.execute(req);
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public ResultSet getResultset()
    {
        return this.res;
    }
    public void getCommit() throws Exception
    {
        this.stat.executeQuery("commit");
    }
    public void getRollBack() throws Exception
    {
        this.stat.executeQuery("rollback");
    }
    public Statement getStat()
    {
        return this.stat;
    }
}

