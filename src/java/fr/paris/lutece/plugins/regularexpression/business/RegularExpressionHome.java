/*
 * Copyright (c) 2002-2009, Mairie de Paris
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

import fr.paris.lutece.plugins.regularexpression.service.RegularExpressionPlugin;
import fr.paris.lutece.portal.business.regularexpression.RegularExpression;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.List;


/**
 * This class provides instances management methods (create, find, ...) for RegularExpression objects
 */
public final class RegularExpressionHome
{
    // Static variable pointed at the DAO instance
    private static IRegularExpressionDAO _dao = (IRegularExpressionDAO) SpringContextService.getPluginBean( RegularExpressionPlugin.PLUGIN_NAME,
            "regularExpressionDAO" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private RegularExpressionHome(  )
    {
    }

    /**
     * Creation of an instance of regular expression
     *
     * @param regularExpression The instance of the RegularExpression which contains the informations to store
     * @param plugin the Plugin
      */
    public static void create( RegularExpression regularExpression, Plugin plugin )
    {
        _dao.insert( regularExpression, plugin );
    }

    /**
     * Update of the regular expression which is specified in parameter
     *
     * @param regularExpression The instance of the RegularExpression which contains the informations to update
     * @param plugin the Plugin
     *
     */
    public static void update( RegularExpression regularExpression, Plugin plugin )
    {
        _dao.store( regularExpression, plugin );
    }

    /**
     * Remove the regular expression whose identifier is specified in parameter
     *
     * @param nIdExpression The reportingProject Id
     * @param plugin the Plugin
     */
    public static void remove( int nIdExpression, Plugin plugin )
    {
        _dao.delete( nIdExpression, plugin );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a regular expression whose identifier is specified in parameter
     *
     * @param nKey The regularExpression primary key
     * @param plugin the Plugin
     * @return an instance of RegularExpression
     */
    public static RegularExpression findByPrimaryKey( int nKey, Plugin plugin )
    {
        return _dao.load( nKey, plugin );
    }

    /**
         * Load the data of all the regular expression and returns them in a  list
         * @param plugin the plugin
         * @return  the list of regularexpression
         */
    public static List<RegularExpression> getList( Plugin plugin )
    {
        return _dao.selectListRegularExpression( plugin );
    }
}
