package edu.eci.pdsw.test;

/*
 * Copyright (C) 2016 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import edu.eci.pdsw.samples.entities.Usuario;
import edu.eci.pdsw.samples.persistence.DaoFactory;
import edu.eci.pdsw.samples.persistence.DaoUsuario;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Properties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 2108616
 */
public class PersistenciaUsuarioTest {
    
    public PersistenciaUsuarioTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void UsuarioNuevo() throws IOException, PersistenceException{         
        InputStream input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
        Properties properties=new Properties();
        properties.load(input);
        
        DaoFactory daof=DaoFactory.getInstance(properties);
               
        try{      
            daof.beginSession();
            DaoUsuario user = daof.getDaoUsuario();
            Usuario userTest = new Usuario("UsuarioTest@mail.com","Usuario test");
            user.save(userTest);
            Usuario userLoadTest = user.load("UsuarioTest@mail.com");
            Assert.assertTrue("No se salvo de forma correcta el usuario", userTest.getEmail().equals(userLoadTest.getEmail()));
            daof.commitTransaction();
        }catch(Exception e){
             fail("Se ha generado un error");
        }finally{
            daof.endSession(); 
        }       
               
    }
    
    @Test
    public void UsuarioYaRegistrado() throws IOException, PersistenceException{         
        InputStream input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
        Properties properties=new Properties();
        properties.load(input);        
        DaoFactory daof=DaoFactory.getInstance(properties);
               
        try{      
            daof.beginSession();
            DaoUsuario user = daof.getDaoUsuario();            
            Usuario userTest = new Usuario("UsuarioTest@mail.com","Usuario test");
            user.save(userTest);
            user.save(userTest);
            fail("Ha permitido salvar a un usuario repetido");          
        }catch(Exception e){
             
        }finally{
            daof.endSession(); 
        }       
               
    }
    
    
}