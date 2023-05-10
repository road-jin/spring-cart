package cart.product.web;

import cart.product.application.ProductService;
import cart.product.web.dto.ProductAdminViewResponse;
import cart.product.web.dto.ProductHomeViewResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductViewController {

    private final ProductService productService;

    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String showHomeView(Model model) {
        model.addAttribute("products", ProductHomeViewResponse.from(productService.findAll()));
        return "index";
    }

    @GetMapping("/admin")
    public String showAdminView(Model model) {
        model.addAttribute("products", ProductAdminViewResponse.from(productService.findAll()));
        return "admin";
    }
}
