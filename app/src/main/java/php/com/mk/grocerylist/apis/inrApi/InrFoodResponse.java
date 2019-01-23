package php.com.mk.grocerylist.apis.inrApi;

import java.util.List;

public class InrFoodResponse {
    private List<InrProduct> list;

    public List<InrProduct> getList() {
        return list;
    }

    public void setList(List<InrProduct> list) {
        this.list = list;
    }
}
