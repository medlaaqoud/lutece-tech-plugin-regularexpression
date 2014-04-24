/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.regularexpression.business;

import fr.paris.lutece.portal.business.regularexpression.RegularExpression;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * This class provides Data Access methods for ReportingFiche objects
 */
public final class RegularExpressionDAO implements IRegularExpressionDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_expression ) FROM regularexpression_regular_expression";
    private static final String SQL_QUERY_FIND_BY_PRIMARY_KEY = "SELECT id_expression,title,regular_expression_value,valid_exemple,information_message,error_message" +
        " FROM  regularexpression_regular_expression WHERE id_expression = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO regularexpression_regular_expression( id_expression,title,regular_expression_value,valid_exemple,information_message,error_message)" +
        "VALUES(?,?,?,?,?,?)";
    private static final String SQL_QUERY_DELETE = "DELETE FROM regularexpression_regular_expression WHERE id_expression = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE regularexpression_regular_expression SET " +
        "id_expression=?,title=?,regular_expression_value=?,valid_exemple=?,information_message=?,error_message=? WHERE id_expression = ? ";
    private static final String SQL_QUERY_SELECT = "SELECT id_expression,title,regular_expression_value,valid_exemple,information_message,error_message" +
        " FROM  regularexpression_regular_expression ";

    /**
     * Generates a new primary key
     *
     * @param plugin the plugin
     * @return The new primary key
     */
    public int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery(  );

        int nKey;

        if ( !daoUtil.next(  ) )
        {
            // if the table is empty
            nKey = 1;
        }

        nKey = daoUtil.getInt( 1 ) + 1;
        daoUtil.free(  );

        return nKey;
    }

    /**
     * Insert a new record in the table.
     *
     * @param regularExpression instance of the RegularExpression object to insert
     * @param plugin the plugin
     */
    public void insert( RegularExpression regularExpression, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        regularExpression.setIdExpression( newPrimaryKey( plugin ) );
        daoUtil.setInt( 1, regularExpression.getIdExpression(  ) );
        daoUtil.setString( 2, regularExpression.getTitle(  ) );
        daoUtil.setString( 3, regularExpression.getValue(  ) );
        daoUtil.setString( 4, regularExpression.getValidExemple(  ) );
        daoUtil.setString( 5, regularExpression.getInformationMessage(  ) );
        daoUtil.setString( 6, regularExpression.getErrorMessage(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of the regular expression from the table
     *
     * @param nId The identifier of the regular expression
     * @param plugin the plugin
     * @return the instance of the RegularExpression
     */
    public RegularExpression load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_PRIMARY_KEY, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery(  );

        RegularExpression regularExpression = null;

        if ( daoUtil.next(  ) )
        {
            regularExpression = new RegularExpression(  );
            regularExpression.setIdExpression( daoUtil.getInt( 1 ) );
            regularExpression.setTitle( daoUtil.getString( 2 ) );
            regularExpression.setValue( daoUtil.getString( 3 ) );
            regularExpression.setValidExemple( daoUtil.getString( 4 ) );
            regularExpression.setInformationMessage( daoUtil.getString( 5 ) );
            regularExpression.setErrorMessage( daoUtil.getString( 6 ) );
        }

        daoUtil.free(  );

        return regularExpression;
    }

    /**
     * Delete a record from the table
     *
     * @param nIdExpression The identifier of the regularExpression
     * @param plugin the plugin
     */
    public void delete( int nIdExpression, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nIdExpression );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Update the regularExpression in the table
     *
     * @param regularExpression instance of the RegularExpression  object to update
     * @param plugin the plugin
     */
    public void store( RegularExpression regularExpression, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        daoUtil.setInt( 1, regularExpression.getIdExpression(  ) );
        daoUtil.setString( 2, regularExpression.getTitle(  ) );
        daoUtil.setString( 3, regularExpression.getValue(  ) );
        daoUtil.setString( 4, regularExpression.getValidExemple(  ) );
        daoUtil.setString( 5, regularExpression.getInformationMessage(  ) );
        daoUtil.setString( 6, regularExpression.getErrorMessage(  ) );
        daoUtil.setInt( 7, regularExpression.getIdExpression(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of all the regularExpression and returns them in a  list
     * @param plugin the plugin
     * @return  the list of regular expression
     */
    public List<RegularExpression> selectListRegularExpression( Plugin plugin )
    {
        List<RegularExpression> regularExpressionList = new ArrayList<RegularExpression>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.executeQuery(  );

        RegularExpression regularExpression = null;

        while ( daoUtil.next(  ) )
        {
            regularExpression = new RegularExpression(  );
            regularExpression.setIdExpression( daoUtil.getInt( 1 ) );
            regularExpression.setTitle( daoUtil.getString( 2 ) );
            regularExpression.setValue( daoUtil.getString( 3 ) );
            regularExpression.setValidExemple( daoUtil.getString( 4 ) );
            regularExpression.setInformationMessage( daoUtil.getString( 5 ) );
            regularExpression.setErrorMessage( daoUtil.getString( 6 ) );
            regularExpressionList.add( regularExpression );
        }

        daoUtil.free(  );

        return regularExpressionList;
    }
}
