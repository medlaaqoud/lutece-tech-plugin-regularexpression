/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
package fr.paris.lutece.plugins.regularexpression.web;

import fr.paris.lutece.plugins.regularexpression.business.RegularExpressionHome;
import fr.paris.lutece.plugins.regularexpression.service.RegularExpressionPlugin;
import fr.paris.lutece.portal.business.regularexpression.RegularExpression;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.regularexpression.IRegularExpressionService;
import fr.paris.lutece.portal.service.regularexpression.RegularExpressionRemovalListenerService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.url.UrlItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;


/**
 * This class provides the user interface to manage regular expression( manage,
 * create, modify, remove)
 */
public class RegularExpressionJspBean extends PluginAdminPageJspBean
{
    public static final String RIGHT_REGULAR_EXPRESSION_MANAGEMENT = "REGULAR_EXPRESSION_MANAGEMENT";

    //	templates
    private static final String TEMPLATE_MANAGE_REGULAR_EXPRESSION = "admin/plugins/regularexpression/manage_regular_expression.html";
    private static final String TEMPLATE_CREATE_REGULAR_EXPRESSION = "admin/plugins/regularexpression/create_regular_expression.html";
    private static final String TEMPLATE_MODIFY_REGULAR_EXPRESSION = "admin/plugins/regularexpression/modify_regular_expression.html";

    //	Markers
    private static final String MARK_EXPRESSION_LIST = "expression_list";
    private static final String MARK_EXPPRESSION = "expression";
    private static final String MARK_PAGINATOR = "paginator";
    private static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";
    private static final String MARK_WEBAPP_URL = "webapp_url";
    private static final String MARK_LOCALE = "locale";

    //	parameters form
    private static final String PARAMETER_ID_EXPRESSION = "id_expression";
    private static final String PARAMETER_TITLE = "title";
    private static final String PARAMETER_VALUE = "value";
    private static final String PARAMETER_VALID_EXEMPLE = "valid_exemple";
    private static final String PARAMETER_INFORMATION_MESSAGE = "information_message";
    private static final String PARAMETER_ERROR_MESSAGE = "error_message";
    private static final String PARAMETER_PAGE_INDEX = "page_index";

    //	 other constants
    private static final String EMPTY_STRING = "";

    //	message
    private static final String MESSAGE_CONFIRM_REMOVE_EXPRESSION = "regularexpression.message.confirm_remove_regular_expression";
    private static final String MESSAGE_MANDATORY_FIELD = "regularexpression.message.mandatory.field";
    private static final String MESSAGE_REGULAR_EXPRESSION_FORMAT_NOT_VALIDE = "regularexpression.message.regular_expression_format_not_valide";
    private static final String MESSAGE_REGULAR_EXPRESSION_EXEMPLE_NOT_VALIDE = "regularexpression.message.regular_expression_exemple_not_valide";
    private static final String MESSAGE_CAN_NOT_REMOVE_REGULAR_EXPRESSION = "regularexpression.message.can_not_remove_regular_expression";
    private static final String FIELD_TITLE = "regularexpression.create_regular_expression.label_title";
    private static final String FIELD_VALUE = "regularexpression.create_regular_expression.label_value";
    private static final String FIELD_VALID_EXEMPLE = "regularexpression.create_regular_expression.label_valid_exemple";
    private static final String FIELD_INFORMATION_MESSAGE = "regularexpression.create_regular_expression.label_information_message";
    private static final String FIELD_ERROR_MESSAGE = "regularexpression.create_regular_expression.label_error_message";

    //	properties
    private static final String PROPERTY_ITEM_PER_PAGE = "regularexpression.itemsPerPage";
    private static final String PROPERTY_MANAGE_REGULAR_EXPRESSION_TITLE = "regularexpression.manage_regular_expression.page_title";
    private static final String PROPERTY_MODIFY_REGULAR_EXPRESSION_TITLE = "regularexpression.modify_regular_expression.title";
    private static final String PROPERTY_CREATE_REGULAR_EXPRESSION_TITLE = "regularexpression.create_regular_expression.title";

    //Jsp Definition
    private static final String JSP_MANAGE_REGULAR_EXPRESSION = "jsp/admin/plugins/regularexpression/ManageRegularExpression.jsp";
    private static final String JSP_DO_REMOVE_REGULAR_EXPRESSION = "jsp/admin/plugins/regularexpression/DoRemoveRegularExpression.jsp";

    //	session fields
    private int _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_ITEM_PER_PAGE, 15 );
    private String _strCurrentPageIndexExport;
    private int _nItemsPerPageForm;

    /**
     * Return management regular expression ( list of regular expression )
     * @param request The Http request
     * @return Html form
     */
    public String getManageRegularExpression( HttpServletRequest request )
    {
        Plugin plugin = getPlugin(  );
        Locale locale = getLocale(  );
        HashMap model = new HashMap(  );
        List<RegularExpression> listRegularExpression = RegularExpressionHome.getList( plugin );

        _strCurrentPageIndexExport = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX,
                _strCurrentPageIndexExport );
        _nItemsPerPageForm = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE,
                _nItemsPerPageForm, _nDefaultItemsPerPage );

        Paginator paginator = new Paginator( listRegularExpression, _nItemsPerPageForm,
                getJspManageRegularExpression( request ), PARAMETER_PAGE_INDEX, _strCurrentPageIndexExport );
        model.put( MARK_PAGINATOR, paginator );
        model.put( MARK_NB_ITEMS_PER_PAGE, EMPTY_STRING + _nItemsPerPageForm );
        model.put( MARK_EXPRESSION_LIST, paginator.getPageItems(  ) );
        setPageTitleProperty( PROPERTY_MANAGE_REGULAR_EXPRESSION_TITLE );

        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_MANAGE_REGULAR_EXPRESSION, locale, model );

        //ReferenceList refMailingList;
        //refMailingList=AdminMailingListService.getMailingLists(adminUser);
        return getAdminPage( templateList.getHtml(  ) );
    }

    /**
     * Gets the regular expressioncreation page
     * @param request The HTTP request
     * @return The regular expression creation page
     */
    public String getCreateRegularExpression( HttpServletRequest request )
    {
        Locale locale = getLocale(  );
        HashMap model = new HashMap(  );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, AdminUserService.getLocale( request ).getLanguage(  ) );
        setPageTitleProperty( PROPERTY_CREATE_REGULAR_EXPRESSION_TITLE );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_REGULAR_EXPRESSION, locale, model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Perform the regular expression  creation
     * @param request The HTTP request
     * @return The URL to go after performing the action
     */
    public String doCreateRegularExpression( HttpServletRequest request )
    {
        RegularExpression regularExpression = new RegularExpression(  );
        String strError = getRegularExpressionData( request, regularExpression );

        if ( strError != null )
        {
            return strError;
        }

        RegularExpressionHome.create( regularExpression, getPlugin(  ) );

        return getJspManageRegularExpression( request );
    }

    /**
     * Gets the regular expression modification page
     * @param request The HTTP request
     * @return The regular expression creation page
     */
    public String getModifyRegularExpression( HttpServletRequest request )
    {
        Plugin plugin = getPlugin(  );
        Locale locale = getLocale(  );
        RegularExpression regularExpression;
        String strIdExpression = request.getParameter( PARAMETER_ID_EXPRESSION );
        HashMap model = new HashMap(  );

        int nIdExpression = -1;

        if ( ( strIdExpression != null ) && !strIdExpression.equals( EMPTY_STRING ) )
        {
            try
            {
                nIdExpression = Integer.parseInt( strIdExpression );
            }
            catch ( NumberFormatException ne )
            {
                AppLogService.error( ne );

                return getManageRegularExpression( request );
            }
        }
        else
        {
            return getManageRegularExpression( request );
        }

        regularExpression = RegularExpressionHome.findByPrimaryKey( nIdExpression, plugin );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, AdminUserService.getLocale( request ).getLanguage(  ) );
        model.put( MARK_EXPPRESSION, regularExpression );
        setPageTitleProperty( PROPERTY_MODIFY_REGULAR_EXPRESSION_TITLE );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_REGULAR_EXPRESSION, locale, model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Perform the regular expression modification
     * @param request The HTTP request
     * @return The URL to go after performing the action
     */
    public String doModifyRegularExpression( HttpServletRequest request )
    {
        Plugin plugin = getPlugin(  );
        RegularExpression regularExpression;
        String strIdExpression = request.getParameter( PARAMETER_ID_EXPRESSION );
        int nIdExpression = -1;

        if ( ( strIdExpression != null ) && !strIdExpression.equals( EMPTY_STRING ) )
        {
            try
            {
                nIdExpression = Integer.parseInt( strIdExpression );
            }
            catch ( NumberFormatException ne )
            {
                AppLogService.error( ne );

                return getJspManageRegularExpression( request );
            }
        }
        else
        {
            return getJspManageRegularExpression( request );
        }

        regularExpression = new RegularExpression(  );
        regularExpression.setIdExpression( nIdExpression );

        String strError = getRegularExpressionData( request, regularExpression );

        if ( strError != null )
        {
            return strError;
        }

        RegularExpressionHome.update( regularExpression, plugin );

        return getJspManageRegularExpression( request );
    }

    /**
     * Gets the confirmation page of delete regular expression
     * @param request The HTTP request
     * @return the confirmation page of delete regular expression
     */
    public String getConfirmRemoveRegularExpression( HttpServletRequest request )
    {
        String strIdExpression = request.getParameter( PARAMETER_ID_EXPRESSION );

        if ( strIdExpression == null )
        {
            return getHomeUrl( request );
        }

        UrlItem url = new UrlItem( JSP_DO_REMOVE_REGULAR_EXPRESSION );
        url.addParameter( PARAMETER_ID_EXPRESSION, strIdExpression );

        return AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_EXPRESSION, url.getUrl(  ),
            AdminMessage.TYPE_CONFIRMATION );
    }

    /**
     * Perform the regular expression supression
     * @param request The HTTP request
     * @return The URL to go after performing the action
     */
    public String doRemoveRegularExpression( HttpServletRequest request )
    {
        String strIdExpression = request.getParameter( PARAMETER_ID_EXPRESSION );
        ArrayList<String> listErrors = new ArrayList<String>(  );

        int nIdExpression = -1;

        if ( ( strIdExpression != null ) && !strIdExpression.equals( EMPTY_STRING ) )
        {
            try
            {
                nIdExpression = Integer.parseInt( strIdExpression );
            }
            catch ( NumberFormatException ne )
            {
                AppLogService.error( ne );
            }
        }

        if ( !RegularExpressionRemovalListenerService.getService(  )
                                                         .checkForRemoval( strIdExpression, listErrors, getLocale(  ) ) )
        {
            String strCause = AdminMessageService.getFormattedList( listErrors, getLocale(  ) );
            Object[] args = { strCause };

            return AdminMessageService.getMessageUrl( request, MESSAGE_CAN_NOT_REMOVE_REGULAR_EXPRESSION, args,
                AdminMessage.TYPE_STOP );
        }

        if ( nIdExpression != -1 )
        {
            RegularExpressionHome.remove( nIdExpression, getPlugin(  ) );
        }

        return getJspManageRegularExpression( request );
    }

    /**
     * Get the request data and if there is no error insert the data in the regularExpression object specified in parameter.
     * return null if there is no error or else return the error page url
     * @param request the request
     * @param regularExpression  the regularExpression Object
     * @return null if there is no error or else return the error page url
     */
    private String getRegularExpressionData( HttpServletRequest request, RegularExpression regularExpression )
    {
        IRegularExpressionService service = (IRegularExpressionService) SpringContextService.getPluginBean( RegularExpressionPlugin.PLUGIN_NAME,
                "regularExpressionService" );
        String strTitle = ( request.getParameter( PARAMETER_TITLE ) == null ) ? null
                                                                              : request.getParameter( PARAMETER_TITLE )
                                                                                       .trim(  );
        String strValue = ( request.getParameter( PARAMETER_VALUE ) == null ) ? null
                                                                              : request.getParameter( PARAMETER_VALUE )
                                                                                       .trim(  );
        String strValidExemple = ( request.getParameter( PARAMETER_VALID_EXEMPLE ) == null ) ? null
                                                                                             : request.getParameter( PARAMETER_VALID_EXEMPLE )
                                                                                                      .trim(  );
        String strInformationMessage = ( request.getParameter( PARAMETER_INFORMATION_MESSAGE ) == null ) ? null
                                                                                                         : request.getParameter( PARAMETER_INFORMATION_MESSAGE )
                                                                                                                  .trim(  );
        String strErrorMessage = ( request.getParameter( PARAMETER_ERROR_MESSAGE ) == null ) ? null
                                                                                             : request.getParameter( PARAMETER_ERROR_MESSAGE )
                                                                                                      .trim(  );

        String strFieldError = EMPTY_STRING;

        if ( ( strTitle == null ) || strTitle.equals( EMPTY_STRING ) )
        {
            strFieldError = FIELD_TITLE;
        }

        else if ( ( strValue == null ) || strValue.equals( EMPTY_STRING ) )
        {
            strFieldError = FIELD_VALUE;
        }

        else if ( ( strValidExemple == null ) || strValidExemple.equals( "" ) )
        {
            strFieldError = FIELD_VALID_EXEMPLE;
        }
        else if ( ( strInformationMessage == null ) || strInformationMessage.equals( "" ) )
        {
            strFieldError = FIELD_INFORMATION_MESSAGE;
        }
        else if ( ( strErrorMessage == null ) || strErrorMessage.equals( "" ) )
        {
            strFieldError = FIELD_ERROR_MESSAGE;
        }

        //Mandatory fields
        if ( !strFieldError.equals( EMPTY_STRING ) )
        {
            Object[] tabRequiredFields = { I18nService.getLocalizedString( strFieldError, getLocale(  ) ) };

            return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_FIELD, tabRequiredFields,
                AdminMessage.TYPE_STOP );
        }

        //	teste if the regular expression is valid
        if ( !service.isPatternValide( strValue ) )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_REGULAR_EXPRESSION_FORMAT_NOT_VALIDE,
                AdminMessage.TYPE_STOP );
        }

        regularExpression.setTitle( strTitle );
        regularExpression.setValue( strValue );
        regularExpression.setValidExemple( strValidExemple );
        regularExpression.setInformationMessage( strInformationMessage );
        regularExpression.setErrorMessage( strErrorMessage );

        if ( !service.isMatches( strValidExemple, strValue ) )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_REGULAR_EXPRESSION_EXEMPLE_NOT_VALIDE,
                AdminMessage.TYPE_STOP );
        }

        return null;
    }

    /**
     * return the url of manage regular expression
     * @param request the request
     * @return the url of manage regular expression
     */
    private String getJspManageRegularExpression( HttpServletRequest request )
    {
        return AppPathService.getBaseUrl( request ) + JSP_MANAGE_REGULAR_EXPRESSION;
    }
}
