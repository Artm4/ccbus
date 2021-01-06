package ccbus.connect.core.ccbus.payload;

import java.util.ArrayList;
import java.util.List;

public class PageData<T> {
    public int total=0;

    public List<T> data=new ArrayList<>();

    public int page=1;
    public int rowsPerPage=50;

    public PageData(){}

    public PageData(List<T> data) {
        this.data = data;
        this.total = data.size();
    }

    public PageData(int total, List<T> data, int page, int rowsPerPage) {
        this.total = total;
        this.data = data;
        this.page = page;
        this.rowsPerPage = rowsPerPage;
    }

    public int getTotal()
    {
        return this.total;
    }

    /**
     * Get sliced data
     * @return List<T>
     */
    public List<T> getSlicedData()
    {
        return __getSlicedData();
    }

    /**
     * Slice internal data if size greater than total
     * @return List<T>
     */
    public List<T> sliceData()
    {
        this.data=__getSlicedData();
        return this.data;
    }

    private List<T> __getSlicedData()
    {
        if(this.data.size()>this.total)
        {
            int limit=(this.page-1)*this.rowsPerPage;
            int limitEffective=limit;
            if(limit>this.data.size())
            {
                limitEffective=this.data.size();
            }

            int rowPerPageEffective=this.rowsPerPage;
            if(this.rowsPerPage>this.data.size())
            {
                rowPerPageEffective=this.data.size();
            }
            // out of bound check
            if((limitEffective+rowPerPageEffective)>this.data.size())
            {
                rowPerPageEffective=rowPerPageEffective-this.data.size();
            }
            return this.data.subList(
                    limitEffective,
                    limitEffective+rowPerPageEffective
            );
        }
        return this.data;
    }

    public List<T> getData()
    {
        return this.data;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void setPage(int page) {
        this.page= page;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public int getPage() {
        return page;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public T getItem(int index)
    {
        return this.data.get(index);
    }

    public int getDataSize()
    {
        return this.data.size();
    }
}
