package us.universalpvp.te.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.NBTTagByte;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class ItemBuilder {

	private ItemStack item = null;
	private ItemMeta meta = null;

	public ItemBuilder(ItemStack item) {
		this.item = item;
		this.createItem(item);
	}

	public ItemBuilder(Material material) {
		this.createItem(material);
	}

	private ItemBuilder createItem(Material material) {

		ItemStack item = new ItemStack(material);
		this.item = item;

		return this;

	}

	private ItemBuilder createItem(ItemStack item) {

		item = new ItemStack(item);
		this.item = item;

		return this;
	}

	public ItemBuilder setName(String name) {

		ItemMeta meta = this.item.getItemMeta();
		this.meta = meta;
		name = ChatColor.translateAlternateColorCodes('&', name);
		meta.setDisplayName(name);

		return this;
	}

	public ItemBuilder setLore(List<String> lore) {

		ItemMeta meta = this.item.getItemMeta();
		this.meta = meta;
		meta.setLore(lore);

		return this;
	}

	public ItemBuilder addEnchant(Enchantment enchantment, int value, boolean bool) {

		ItemMeta meta = this.item.getItemMeta();
		this.meta = meta;
		meta.addEnchant(enchantment, value, bool);

		return this;
	}

	public ItemBuilder removeEnchant(Enchantment enchantment) {

		ItemMeta meta = this.item.getItemMeta();
		this.meta = meta;
		meta.removeEnchant(enchantment);

		return this;
	}

	public ItemBuilder addItemFlag(ItemFlag flag) {

		ItemMeta meta = this.item.getItemMeta();
		this.meta = meta;
		meta.addItemFlags(flag);

		return this;

	}

	public ItemBuilder removeItemFlag(ItemFlag flag) {

		ItemMeta meta = this.item.getItemMeta();
		this.meta = meta;
		meta.removeItemFlags(flag);

		return this;

	}

	public ItemBuilder setDurablility(short durability) {

		item.setDurability(durability);

		return this;
	}

	public ItemBuilder setUnbreakable(boolean unbreakable) {

		ItemMeta meta = this.item.getItemMeta();
		this.meta = meta;
		meta.spigot().setUnbreakable(true);

		return this;
	}

	public ItemBuilder setMaterialData(MaterialData data) {

		item.setData(data);

		return this;
	}

	public ItemBuilder setLeatherArmorColor(Color color) {

		if (!(meta instanceof LeatherArmorMeta))
			throw new IllegalArgumentException(
					"Can't invoke method on this AdvancedItem! Required meta: LeatherArmorMeta");
		LeatherArmorMeta leatherMeta = (LeatherArmorMeta) meta;
		leatherMeta.setColor(color);
		item.setItemMeta(leatherMeta);

		return this;
	}

	public ItemBuilder setAmount(int amount) {
		item.setAmount(amount);

		return this;
	}

	public ItemBuilder addBookPage(String... page) {

		BookMeta bookMeta = (BookMeta) meta;
		bookMeta.addPage(page);
		item.setItemMeta(bookMeta);

		return this;
	}

	public ItemBuilder setBookPage(int page, String data) {

		BookMeta bookMeta = (BookMeta) meta;
		bookMeta.setPage(page, data);
		item.setItemMeta(bookMeta);

		return this;
	}

	public ItemBuilder setTitle(String title) {

		BookMeta bookMeta = (BookMeta) meta;
		bookMeta.setTitle(title);
		item.setItemMeta(bookMeta);

		return this;
	}

	public ItemBuilder setBannerPattern(int index, Pattern pattern) {

		BannerMeta bannerMeta = (BannerMeta) meta;
		bannerMeta.setPattern(index, pattern);
		item.setItemMeta(bannerMeta);

		return this;
	}

	public ItemBuilder setBannerBaseColor(DyeColor color) {

		BannerMeta bannerMeta = (BannerMeta) meta;
		bannerMeta.setBaseColor(color);
		item.setItemMeta(bannerMeta);

		return this;
	}

	public ItemBuilder setNBTTagCompound(String NBTTagCompound) {

		net.minecraft.server.v1_8_R3.ItemStack stack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
		tag.set(NBTTagCompound, new NBTTagByte((byte) 1));
		stack.setTag(tag);
		item = CraftItemStack.asBukkitCopy(stack);

		return this;
	}

	public ItemBuilder setItemAge(byte age) {

		net.minecraft.server.v1_8_R3.ItemStack stack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
		tag.set("Age", new NBTTagByte(age));
		stack.setTag(tag);
		item = CraftItemStack.asBukkitCopy(stack);

		return this;

	}

	public ItemBuilder setSkullOwner(String player) {

		SkullMeta skullMeta = (SkullMeta) meta;
		skullMeta.setOwner(player);

		return this;
	}

	public ItemBuilder setSkullOwner(Player player) {

		SkullMeta skullMeta = (SkullMeta) meta;
		skullMeta.setOwner(player.getName());

		return this;
	}

	public ItemBuilder setSkullOwner(UUID uuid) {

		SkullMeta skullMeta = (SkullMeta) meta;
		skullMeta.setOwner(Bukkit.getPlayer(uuid).getName());

		return this;
	}

	public String getName() {

		ItemMeta meta = this.item.getItemMeta();
		this.meta = meta;
		String name = meta.getDisplayName();

		return name;

	}

	public Map<Enchantment, Integer> getEnchants() {

		ItemMeta meta = this.item.getItemMeta();
		this.meta = meta;
		Map<Enchantment, Integer> enchants = meta.getEnchants();

		return enchants;

	}

	public List<String> getLore() {

		ItemMeta meta = this.item.getItemMeta();
		this.meta = meta;
		List<String> list = meta.getLore();

		return list;

	}

	public Set<ItemFlag> getItemFlags() {

		ItemMeta meta = this.item.getItemMeta();
		this.meta = meta;
		Set<ItemFlag> flags = meta.getItemFlags();

		return flags;

	}

	public int getBookPage(int pageNumber) {

		BookMeta bookMeta = (BookMeta) meta;
		bookMeta.getPage(pageNumber);

		return pageNumber;
	}

	public int getBookPageCount() {

		BookMeta bookMeta = (BookMeta) meta;
		int pageCount = bookMeta.getPageCount();

		return pageCount;
	}

	public List<String> getBookPages() {

		BookMeta bookMeta = (BookMeta) meta;
		List<String> pages = bookMeta.getPages();

		return pages;
	}

	public String getBookAuthor() {

		BookMeta bookMeta = (BookMeta) meta;
		String author = bookMeta.getAuthor();

		return author;
	}

	public String getBookTitle() {

		BookMeta bookMeta = (BookMeta) meta;
		String title = bookMeta.getTitle();

		return title;
	}

	public List<Pattern> getBannerPatterns() {

		BannerMeta bannerMeta = (BannerMeta) meta;
		List<Pattern> patterns = bannerMeta.getPatterns();

		return patterns;

	}

	public DyeColor getBannerBaseColor() {

		BannerMeta bannerMeta = (BannerMeta) meta;
		DyeColor color = bannerMeta.getBaseColor();

		return color;
	}

	public Player getSkullOwner() {

		SkullMeta skullMeta = (SkullMeta) meta;
		Player p = Bukkit.getPlayer(skullMeta.getOwner());

		return p;
	}

	public ItemStack clone() {
		return item.clone();
	}

	public boolean hasItemFlag(ItemFlag flag) {

		if (!getItemFlags().contains(flag)) {
			return false;
		} else {
			return true;
		}

	}

	public boolean hasEnchant(Enchantment enchant) {

		if (!getEnchants().containsKey(enchant)) {
			return false;
		} else {
			return true;
		}

	}

	public boolean hasLore() {

		ItemMeta meta = item.getItemMeta();
		this.meta = meta;
		if (!meta.hasLore()) {
			return false;
		} else {
			return true;
		}

	}

	public boolean hasBookAuthor() {

		BookMeta bookMeta = (BookMeta) meta;

		if (!bookMeta.hasAuthor()) {
			return false;
		} else {
			return true;
		}

	}

	public boolean hasBookPages() {

		BookMeta bookMeta = (BookMeta) meta;

		if (!bookMeta.hasPages()) {
			return false;
		} else {
			return true;
		}

	}

	public boolean hasBookTitle() {

		BookMeta bookMeta = (BookMeta) meta;

		if (!bookMeta.hasTitle()) {
			return false;
		} else {
			return true;
		}

	}

	public boolean hasSkullOwner() {

		SkullMeta skullMeta = (SkullMeta) meta;
		if (!skullMeta.hasOwner()) {
			return false;
		} else {
			return true;
		}
	}

	public void addToInventory(Inventory inv) {
		inv.addItem(item.clone());
	}

	public void buildItem() {

		item.setItemMeta(this.meta);

	}

}
