/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import net.daw.bean.ReplyBean;
import net.daw.bean.UsuarioBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.UsuarioDao;
import net.daw.factory.ConnectionFactory;
import net.daw.helper.ParameterCook;

/**
 *
 * @author Ramón
 */
public class UsuarioService {

    HttpServletRequest oRequest;
    String ob = null;

    public UsuarioService(HttpServletRequest oRequest) {
        super();
        this.oRequest = oRequest;
        ob = oRequest.getParameter("ob");
    }

    protected Boolean checkPermission(String strMethodName) {
        UsuarioBean oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
        if (oUsuarioBean != null) {
            return true;
        } else {
            return false;
        }
    }

    public ReplyBean get() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        if (this.checkPermission("get")) {
            try {
                Integer id = Integer.parseInt(oRequest.getParameter("id"));
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
                UsuarioBean oUsuarioBean = oUsuarioDao.get(id, 1);
                // Gson oGson = new Gson();
                Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
                oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: get method: " + ob + " object", ex);
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean remove() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        if (this.checkPermission("remove")) {
            try {
                Integer id = Integer.parseInt(oRequest.getParameter("id"));
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
                int iRes = oUsuarioDao.remove(id);
                oReplyBean = new ReplyBean(200, Integer.toString(iRes));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: remove method: " + ob + " object", ex);
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean getcount() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        if (this.checkPermission("getcount")) {
            try {
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
                int registros = oUsuarioDao.getcount();
                Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
                oReplyBean = new ReplyBean(200, oGson.toJson(registros));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: getcount method: " + ob + " object", ex);
            } finally {
                oConnectionPool.disposeConnection();
            }

        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean create() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        if (this.checkPermission("create")) {
            try {
                String strJsonFromClient = oRequest.getParameter("json");
                Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
                UsuarioBean oUsuarioBean = new UsuarioBean();
                oUsuarioBean = oGson.fromJson(strJsonFromClient, UsuarioBean.class);
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
                oUsuarioBean = oUsuarioDao.create(oUsuarioBean);
                oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: create method: " + ob + " object", ex);
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean update() throws Exception {
        int iRes = 0;
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        if (this.checkPermission("update")) {
            try {
                String strJsonFromClient = oRequest.getParameter("json");
                Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
                UsuarioBean oUsuarioBean = new UsuarioBean();
                oUsuarioBean = oGson.fromJson(strJsonFromClient, UsuarioBean.class);
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
                iRes = oUsuarioDao.update(oUsuarioBean);
                oReplyBean = new ReplyBean(200, Integer.toString(iRes));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: update method: " + ob + " object", ex);
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean getpage() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        if (this.checkPermission("getpage")) {
            try {
                Integer iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
                Integer iPage = Integer.parseInt(oRequest.getParameter("page"));
                HashMap<String, String> hmOrder = ParameterCook.getOrderParams(oRequest.getParameter("order"));
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
                ArrayList<UsuarioBean> alUsuarioBean = oUsuarioDao.getpage(iRpp, iPage, hmOrder, 1);
                Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
                oReplyBean = new ReplyBean(200, oGson.toJson(alUsuarioBean));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: get page: " + ob + " object", ex);
            } finally {
                oConnectionPool.disposeConnection();
            }

        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean fill() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        if (this.checkPermission("fill")) {
            try {
                Integer number = Integer.parseInt(oRequest.getParameter("number"));
                Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                String[] dni = {"9654123F", "25896321P", "96325812H", "75395182R", "98741236L"};
                String[] nombre = {"Carlos", "Victor", "Manuel", "Maria", " Lucia"};
                String[] ape1 = {"Martinez", "Garcia", "Perez", "Gonzalez", "Marquez"};
                String[] ape2 = {"Martinez", "Garcia", "Perez", "Gonzalez", "Marquez"};
                String[] login = {"bemu", "poka", "moci", "pofu", "mita"};
                String[] pass = {"12345", "aeiou", "qwert", "abcde", "00000"};
                int[] id_tipoUsuario = {1, 2};
                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
                UsuarioBean oUsuarioBean = new UsuarioBean();
                for (int i = 1; i <= number; i++) {
                    oUsuarioBean.setDni(dni[randomMath(dni.length)]);
                oUsuarioBean.setNombre(nombre[randomMath(nombre.length)]);
                oUsuarioBean.setApe1(ape1[randomMath(ape1.length)]);
                oUsuarioBean.setApe2(ape2[randomMath(ape2.length)]);
                oUsuarioBean.setLogin(login[randomMath(login.length)]);
                oUsuarioBean.setPass(pass[randomMath(pass.length)]);
                oUsuarioBean.setId_tipoUsuario(id_tipoUsuario[randomMath(id_tipoUsuario.length)]);
                    oUsuarioBean = oUsuarioDao.create(oUsuarioBean);
                }
                oReplyBean = new ReplyBean(200, oGson.toJson(number));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: create method: " + ob + " object", ex);
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }
    
    private int randomMath(int number) {
		return (int) (Math.random() * number);
	}

    public ReplyBean login() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        String strLogin = oRequest.getParameter("user");
        String strPassword = oRequest.getParameter("pass");

        oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
        oConnection = oConnectionPool.newConnection();
        UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);

        UsuarioBean oUsuarioBean = oUsuarioDao.login(strLogin, strPassword);
        if (oUsuarioBean.getId() > 0) {
            oRequest.getSession().setAttribute("user", oUsuarioBean);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
        } else {
            //throw new Exception("ERROR Bad Authentication: Service level: get page: " + ob + " object");
            oReplyBean = new ReplyBean(401, "Bad Authentication");
        }
        return oReplyBean;
    }

    public ReplyBean logout() throws Exception {
        oRequest.getSession().invalidate();
        return new ReplyBean(200, "OK");
    }

    public ReplyBean check() throws Exception {
        ReplyBean oReplyBean;
        UsuarioBean oUsuarioBean;
        oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
        if (oUsuarioBean != null) {
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
        } else {
            oReplyBean = new ReplyBean(401, "No active session");
        }
        return oReplyBean;
    }

}
