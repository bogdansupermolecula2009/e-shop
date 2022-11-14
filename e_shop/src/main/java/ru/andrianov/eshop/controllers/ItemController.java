package ru.andrianov.eshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.andrianov.eshop.models.Customer;
import ru.andrianov.eshop.models.Item;
import ru.andrianov.eshop.repositories.ItemRepository;
import ru.andrianov.eshop.security.CustomerDetails;
import ru.andrianov.eshop.services.AdminService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final AdminService adminService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/shop")
    public String getItems(Model model, Authentication authentication) {
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        Customer customer = customerDetails.getCustomer();
        model.addAttribute("username", customer.getUsername());

        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "shop";
    }

    @GetMapping("/admin/addItem")
    public String addPage(@ModelAttribute("item") Item item) {
        adminService.doAdminStuff();
        return "/admin/addItem";
    }

    @PostMapping("/admin/addItem")
    public String addItem(@RequestParam String name,
                          @RequestParam BigDecimal price,
                          @RequestParam String url) {
        adminService.doAdminStuff();
        Item item = Item.builder()
                .name(name)
                .price(price)
                .url(url)
                .build();
        itemRepository.save(item);
        return "redirect:/shop";
    }

    @GetMapping("/admin/editItem")
    public String editPage(@ModelAttribute("item") Item item) {
        adminService.doAdminStuff();
        return "/admin/editItem";
    }

    @PostMapping("/admin/editItem")
    public String updateProduct(@RequestParam long id,
                                @RequestParam String name,
                                @RequestParam BigDecimal price,
                                @RequestParam String url) {
        adminService.doAdminStuff();
        Item item = itemRepository.getReferenceById(id);
        item.setName(name);
        item.setPrice(price);
        item.setUrl(url);

        itemRepository.save(item);

        return "redirect:/shop";
    }

    @GetMapping("/admin/deleteItem")
    public String deletePage(@ModelAttribute("item") Item item) {
        adminService.doAdminStuff();
        return "/admin/deleteItem";
    }

    @PostMapping("/admin/deleteItem")
    public String deleteItem(@RequestParam long id) {
        adminService.doAdminStuff();
        itemRepository.deleteById(id);
        return "redirect:/shop";
    }

}
