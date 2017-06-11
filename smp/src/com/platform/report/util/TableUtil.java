package com.platform.report.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.platform.report.po.ColumnTitle;
import com.platform.report.po.RowTitle;
import com.platform.report.po.Table;
import com.platform.report.po.Title;

/**
 *  <p>
 *  Table������
 *  </p>
 *  @author ZDL ʱ�� Jan 9, 2013 4:06:10 PM
 *  @version 1.0 
 *  </br>
 *  ����޸��� ��
 * 
 */
public class TableUtil {
    
    /**
     * 
     * <p>
     * ���ļ���������ʽ
     * </p>
     * @author ZDL ʱ�� Jan 10, 2013 9:36:02 AM
     * @param filePath �ļ�·��
     * @return
     * @throws JDOMException 
     * @throws IOException 
     */
    public Table parseTable(String filePath) throws JDOMException, IOException {
        Table tableFormat = new Table();
        SAXBuilder builder = new SAXBuilder();
        try{
            Document document = builder.build(new File(filePath));
            Element root = document.getRootElement();
            
            // 1����ȡ��������
            // �����ʽ class
            String tableCssClass = getAttributeValue(root, "cssClass");
            if(tableCssClass != null) {
                tableFormat.setTableCssClass(tableCssClass);
            }
            
            String tableId = getAttributeValue(root, "tableId");
            if(tableId != null) {
                tableFormat.setTableId(tableId);
            }
            
            // �����ʽ style
            String tableCssStyle = getAttributeValue(root, "cssStyle");
            if(tableCssStyle != null) {
                tableFormat.setTableCssStyle(tableCssStyle);
            }
            
            // �����ʽ cellspacing
            String cellspacing = getAttributeValue(root, "cellspacing");
            if(cellspacing != null) {
                tableFormat.setCellspacing(cellspacing);
            }
            
            // �����ʽ cellpadding
            String cellpadding = getAttributeValue(root, "cellpadding");
            if(cellpadding != null) {
                tableFormat.setCellpadding(cellpadding);
            }
            
            // �����ʽ border
            String border = getAttributeValue(root, "border");
            if(border != null) {
                tableFormat.setBorder(border);
            }
            
            // �����ʽ border
            String trClassOne = getAttributeValue(root, "trClassOne");
            if(trClassOne != null) {
                tableFormat.setTrClassOne(trClassOne);
            }
            
            // �����ʽ border
            String trClassTwo = getAttributeValue(root, "trClassTwo");
            if(trClassTwo != null) {
                tableFormat.setTrClassTwo(trClassTwo);
            }
            
            // �����
            String tableWidth = getAttributeValue(root, "width");
            if(tableWidth != null) {
                tableFormat.setTableWidth(tableWidth);
            }
            
            // �Ƿ���ʾ������⣬Ĭ����ʾ��true��
            String showTitle = getAttributeValue(root, "showTitle");
            if(showTitle != null) {
                tableFormat.setShowTitle(Boolean.valueOf(showTitle));
            }
            
            // ������Ͻ�
            String leftTop = getAttributeValue(root, "leftTop");
            if(leftTop != null) {
                tableFormat.setLeftTop(leftTop);
            }
            
			// �鿴��ϸ
			String viewDetail = getAttributeValue(root, "viewDetail");
			if (viewDetail != null && "true".equals(viewDetail)) {
				tableFormat.setViewDetail(true);
			}
            
            // 2�������б��⡢�б���
            Element columnRoot = root.getChild("columns");
            List<Element> columnTitleList = columnRoot.getChildren("column");
            
            // ��֤�б����Ƿ�Ϊ��
            if(columnTitleList == null || columnTitleList.size()==0) {
                return null;
            }
            
            // ��֤�б����Ƿ�Ϊ��
            Element rowRoot = root.getChild("rows");
            List<Element> rowTitleList = rowRoot.getChildren("row");
            if(rowTitleList == null || rowTitleList.size()==0) {
                return null;
            }
            
            // 3���б���
            List<ColumnTitle> cList = new ArrayList<ColumnTitle>();
            parseColumnElementList(cList, columnTitleList, null,0);
            tableFormat.setColumnTitleList(cList);
            
            // 4���б���
            List<RowTitle> rList = new ArrayList<RowTitle>();
            parseRowElementList(rList, rowTitleList, null,0);
            tableFormat.setRowTitleList(rList);
            
        }catch(JDOMException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
        return tableFormat;
    }
    
    /**
     * 
     * <p>
     * �����б���
     * </p>
     * @author ZDL ʱ�� Jan 11, 2013 2:45:24 PM
     * @param result ��Ž�����Ľ��
     * @param list Ҫ������list
     * @param parent ������
     */
    private void parseColumnElementList(List<ColumnTitle> result, List<Element> list, ColumnTitle parent,int seriNo) {
        for(int i=0; i<list.size(); i++) {
            Element ele = list.get(i);
            ColumnTitle colTitle = parseColumnElement(ele);
            
            //���ø����⡢���⼶��
            if(parent != null) {
                colTitle.setParentColumnName(parent.getName());
            }
            
            result.add(colTitle);
            
            Element subCol = ele.getChild("subColumns");
            if(subCol != null) {
                List<Element> subColList = subCol.getChildren("column");
                if(subColList != null && subColList.size() !=0) {
                    parseColumnElementList(result,subColList, colTitle,seriNo);
                }
            }
        }
    }
    
    /**
     * 
     * <p>
     * �����б���
     * </p>
     * @author ZDL ʱ�� Jan 11, 2013 2:45:24 PM
     * @param result ��Ž�����Ľ��
     * @param list Ҫ������list
     * @param parent ������
     */
    private void parseRowElementList(List<RowTitle> result, List<Element> list, RowTitle parent,int seriNo) {
        for(int i=0; i<list.size(); i++) {
            Element ele = list.get(i);
            RowTitle rowTitle = parseRowElement(ele);
            
            //��¼������
            if(parent != null) {
                rowTitle.setParentRowName(parent.getName());
            }
            
            result.add(rowTitle);
            
            Element subRow = ele.getChild("subRows");
            if(subRow != null) {
                List<Element> subRowList = subRow.getChildren("row");
                if(subRowList != null && subRowList.size() !=0) {
                    parseRowElementList(result,subRowList, rowTitle,seriNo);
                }
            }
        }
    }
    
    /**
     * 
     * <p>
     * ����XML Element
     * </p>
     * @author ZDL ʱ�� Jan 11, 2013 9:20:54 AM
     * @param column
     * @param elementType row:�б��⣻column:�б���;null:Element
     * @return 
     */
    private Title parseElement(Element element,String elementType) {
        if(element == null) {
            return null;
        }
        Title ct = null;
        if(elementType == null) {
            ct = new Title();
        }else if("row".equals(elementType)) {
            ct = new RowTitle();
        }else if("column".equals(elementType)) {
            ct = new ColumnTitle();
        }
        
        ct.setName(getAttributeValue(element,"name"));
        ct.setDisplayName(getAttributeValue(element, "displayName"));
        ct.setWidth(getAttributeValue(element, "width"));
        ct.setCssClass(getAttributeValue(element, "cssClass"));
        ct.setCssStyle(getAttributeValue(element, "cssStyle"));
        ct.setContent(element.getTextTrim());
        String viewDetail = getAttributeValue(element, "viewDetail");
        if(viewDetail != null && "true".equals(viewDetail)) {
        	ct.setViewDetail(true);
        }
        
        //չʾ���� ��Ĭ��Number
        String showType = getAttributeValue(element, "showType");
        if(showType == null || showType.equals("")) {
            showType = Table.SHOW_TYPE_NUMBER;
            ct.setShowType(Table.SHOW_TYPE_NUMBER);
        }else {
            ct.setShowType(showType);
        }
        
        //С��λ��
        if(showType.equals(Table.SHOW_TYPE_NUMBER) || showType.equals(Table.SHOW_TYPE_PERCENT)) {
            String strDecimals = getAttributeValue(element, "decimals");
            if(strDecimals == null) {
                ct.setDecimals(0);
            }else {
                ct.setDecimals(Integer.valueOf(strDecimals));
            }
        }
        
        //�Ƿ������
        String isCalcu = getAttributeValue(element, "isCalcu");
        if(isCalcu == null) {
            ct.setCalcu(false);
        }else {
            ct.setCalcu(Boolean.valueOf(isCalcu));
        }
        return ct;
    }
    
    /**
     * 
     * <p>
     * �õ����Ե�ֵ
     * </p>
     * @author ZDL ʱ�� Jan 11, 2013 9:26:54 AM
     * @param element 
     * @param attributeName ��������
     * @return
     */
    private String getAttributeValue(Element element,String attributeName) {
        String attributeValue = element.getAttributeValue(attributeName);
        if(attributeValue == null || attributeValue.equals("")) {
            attributeValue = element.getChildText(attributeName);
        }
        if(attributeValue == null || attributeValue.equals("")) {
            return null;
        }
        return attributeValue;
    }
    
    /**
     * 
     * <p>
     * ���ļ��н�����Column Elementת��Ϊjava ColumnTitle����
     * </p>
     * @author ZDL ʱ�� Jan 10, 2013 5:18:59 PM
     * @param column
     * @return
     */
    private ColumnTitle parseColumnElement(Element column) {
        if(column == null) {
            return null;
        }
        ColumnTitle ct = (ColumnTitle)parseElement(column,"column");
        //�����б��ʽ
        if(ct.isCalcu()) {
            ct.setExpression(getAttributeValue(column, "expression"));
        }
        return ct;
    }
    
    /**
     * 
     * <p>
     * ���ļ��н�����Row Elementת��Ϊjava RowTitle����
     * </p>
     * @author ZDL ʱ�� Jan 10, 2013 5:18:59 PM
     * @param column
     * @return 
     */
    private RowTitle parseRowElement(Element row) {
        if(row == null) {
            return null;
        }
        RowTitle ct = (RowTitle)parseElement(row,"row");
        
        //�����б��ʽ
        if(ct.isCalcu()) {
            List<Element> expressionList = row.getChildren("expression");
            if(expressionList != null) {
                List<Title> result = new ArrayList<Title>();
                for(int i=0; i<expressionList.size(); i++) {
                	Title e = parseElement(expressionList.get(i),null);
                    result.add(e);
                }
                ct.setExpressions(result);
            }
        }
        return ct;
    }
    
}

