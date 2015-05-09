package com.smny.wifiAlliance.test;

import java.util.ArrayList;
import java.util.List;

public class PageModel<T> {
    private int page = 1; // ��ǰҳ
    public int totalPages = 0; // ��ҳ��
    private int pageRecorders;// ÿҳ5������
    private int totalRows = 0; // ��������
    private int pageStartRow = 0;// ÿҳ����ʼ��
    private int pageEndRow = 0; // ÿҳ��ʾ���ݵ���ֹ��
    private boolean hasNextPage = false; // �Ƿ�����һҳ
    private boolean hasPreviousPage = false; // �Ƿ���ǰһҳ
    private List<T> list;

    /**
     * 
     * @param list
     * @param pageRecorders
     */
    public PageModel(List<T> list, int pageRecorders) {
        init(list, pageRecorders);// ͨ�����󼯣���¼��������
    }

    /** */
    /**
     * ��ʼ��list������֮��listÿҳ�ļ�¼��
     * 
     * @param list
     * @param pageRecorders
     */
    public void init(List<T> list, int pageRecorders) {
        this.pageRecorders = pageRecorders;
        this.list = list;
        totalRows = list.size();

        hasPreviousPage = false;

        if ((totalRows % pageRecorders) == 0) {
            totalPages = totalRows / pageRecorders;
        } else {
            totalPages = totalRows / pageRecorders + 1;
        }

        if (page >= totalPages) {
            hasNextPage = false;
        } else {
            hasNextPage = true;
        }

        if (totalRows < pageRecorders) {
            this.pageStartRow = 0;
            this.pageEndRow = totalRows;
        } else {
            this.pageStartRow = 0;
            this.pageEndRow = pageRecorders;
        }
    }

    public String displayForPage(String method) {
        StringBuffer sb = new StringBuffer();
        sb.append("<div class=\"w-page\"><label class=\"fr\">");

        // �ж��Ƿ�����һҳ
        if (this.isHasPreviousPage()) {
            sb.append("<a href=\"javascript:void(0)\" onclick=\"" + method
                    + "('" + String.valueOf(this.getPage() - 1)
                    + "');return false;\">��һҳ</a>");
        } else {
            sb.append("<span class=\"disabled\">��һҳ</span>");
        }

        // �м���ʾ
        for (int i = 1; i <= this.getTotalPages(); i++) {
            String spanClzz = "<a href=\"javascript:void(0)\" onclick=\""
                    + method + "('" + String.valueOf(i) + "');return false;\">"
                    + i + "</a>";

            if (this.page == i) {
                spanClzz = "<span class='current'>" + i + "</span>";
            }
            sb.append(spanClzz);

            // ������9ҳ����ʱ��Ž��з�ҳ��ʾ
            if (this.getTotalPages() - 2 > 7) {
                if (i == 5) {
                    i = this.getTotalPages() - 2;
                    sb.append("...");
                }
            }
        }
        // �ж��Ƿ�����һҳ
        if (this.isHasNextPage()) {
            sb.append("<a href=\"javascript:void(0)\" onclick=\"" + method
                    + "('" + String.valueOf(this.getPage() + 1)
                    + "');return false;\">��һҳ</a>");

        } else {
            sb.append("<span class=\"disabled\">��һҳ</span>");
        }

        sb.append("</div>");
        return sb.toString();
    }

    public String displayForPage() {
        StringBuffer sb = new StringBuffer();
        sb.append("<div class=\"w-page\"><label class=\"fr\">");

        // �ж��Ƿ�����һҳ
        if (this.isHasPreviousPage()) {
            sb
                    .append("<a href=\"javascript:void(0)\" onclick=\"findDistResult('"
                            + String.valueOf(this.getPage() - 1)
                            + "');return false;\">��һҳ</a>");
        } else {
            sb.append("<span class=\"disabled\">��һҳ</span>");
        }

        // �м���ʾ
        for (int i = 1; i <= this.getTotalPages(); i++) {
            String spanClzz = "<a href=\"javascript:void(0)\" onclick=\"findDistResult('"
                    + String.valueOf(i) + "');return false;\">" + i + "</a>";

            if (this.page == i) {
                spanClzz = "<span class='current'>" + i + "</span>";
            }
            sb.append(spanClzz);

            // ������9ҳ����ʱ��Ž��з�ҳ��ʾ
            if (this.getTotalPages() - 2 > 7) {
                if (i == 5) {
                    i = this.getTotalPages() - 2;
                    sb.append("...");
                }
            }
        }
        // �ж��Ƿ�����һҳ
        if (this.isHasNextPage()) {
            sb
                    .append("<a href=\"javascript:void(0)\" onclick=\"findDistResult('"
                            + String.valueOf(this.getPage() + 1)
                            + "');return false;\">��һҳ</a>");

        } else {
            sb.append("<span class=\"disabled\">��һҳ</span>");
        }

        sb.append("</div>");
        return sb.toString();
    }

    // �ж�Ҫ��Ҫ��ҳ
    public boolean isNext() {
        return list.size() > 5;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    /**
     * 
     * @return
     */
    public List<T> getNextPage() {
        page = page + 1;
        disposePage();
        return getObjects(page);
    }

    /**
     * �����ҳ
     */
    private void disposePage() {
        if (page == 0) {
            page = 1;
        }
        if ((page - 1) > 0) {
            hasPreviousPage = true;
        } else {
            hasPreviousPage = false;
        }

        if (page >= totalPages) {
            hasNextPage = false;
        } else {
            hasNextPage = true;
        }
    }

    /**
     * ��һҳ
     * 
     * @return
     */
    public List<T> getPreviousPage() {
        page = page - 1;

        if ((page - 1) > 0) {
            hasPreviousPage = true;
        } else {
            hasPreviousPage = false;
        }
        if (page >= totalPages) {
            hasNextPage = false;
        } else {
            hasNextPage = true;
        }
        return getObjects(page);
    }

    /**
     * ��ȡ�ڼ�ҳ������
     * 
     * @param page
     * @return
     */
    public List<T> getObjects(int page) {
        if (page == 0) {
            this.setPage(1);
            page = 1;
        } else {
            this.setPage(page);
        }

        this.disposePage();

        if (page * pageRecorders < totalRows) {// �ж��Ƿ�Ϊ���һҳ
            pageEndRow = page * pageRecorders;
            pageStartRow = pageEndRow - pageRecorders;
        } else {
            pageEndRow = totalRows;
            pageStartRow = pageRecorders * (totalPages - 1);
        }

        List<T> objects = null;
        if (!list.isEmpty()) {
            objects = list.subList(pageStartRow, pageEndRow);
        }
        return objects;
    }

    /**
     * ��һҳ
     * 
     * @return
     */
    public List<T> getFistPage() {
        if (this.isNext()) {
            return list.subList(0, pageRecorders);
        } else {
            return list;
        }
    }

    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page
     *            the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return the totalPages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * @param totalPages
     *            the totalPages to set
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * @return the pageRecorders
     */
    public int getPageRecorders() {
        return pageRecorders;
    }

    /**
     * @param pageRecorders
     *            the pageRecorders to set
     */
    public void setPageRecorders(int pageRecorders) {
        this.pageRecorders = pageRecorders;
    }

    /**
     * @return the totalRows
     */
    public int getTotalRows() {
        return totalRows;
    }

    /**
     * @param totalRows
     *            the totalRows to set
     */
    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    /**
     * @return the pageStartRow
     */
    public int getPageStartRow() {
        return pageStartRow;
    }

    /**
     * @param pageStartRow
     *            the pageStartRow to set
     */
    public void setPageStartRow(int pageStartRow) {
        this.pageStartRow = pageStartRow;
    }

    /**
     * @return the pageEndRow
     */
    public int getPageEndRow() {
        return pageEndRow;
    }

    /**
     * @param pageEndRow
     *            the pageEndRow to set
     */
    public void setPageEndRow(int pageEndRow) {
        this.pageEndRow = pageEndRow;
    }

    /**
     * @return the hasNextPage
     */
    public boolean isHasNextPage() {
        return hasNextPage;
    }

    /**
     * @param hasNextPage
     *            the hasNextPage to set
     */
    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    /**
     * @return the list
     */
    public List<T> getList() {
        return list;
    }

    /**
     * @param list
     *            the list to set
     */
    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * @return the hasPreviousPage
     */
    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    /*
     * 
     * @param args
     */
    public static void main(String[] args) {

        List<String> list = new ArrayList<String>();

        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("g");
        list.add("h");
        list.add("h");
        list.add("i");
        list.add("j");

        System.out.println(list.size());

        PageModel<String> pm = new PageModel<String>(list, 3);// ÿҳ��ʾ����

        pm.getObjects(1);

        System.out.println(pm.displayForPage("disResult"));
    }

}