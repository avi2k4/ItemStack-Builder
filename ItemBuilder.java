package us.universalpvp.te.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.MaterialData;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.NBTTagByte;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class ItemBuilder {

	private ItemStack item = null;
	private ItemMeta meta = null;

	public ItemBuilder(ItemStack item) {
		this.createItem(item);
	}

	public ItemBuilder(Material material) {
		this.createItem(material);
	}

	@Nullable
	private ItemBuilder createItem(Material material) {

		this.item = new ItemStack(material);
		this.meta = item.getItemMeta();
		
		return this;

	}

	@Nullable
	private ItemBuilder createItem(ItemStack item) {

		this.item = item.clone();
		this.meta = item.getItemMeta();

		return this;
	}

	public ItemBuilder setName(String name) {

		name = ChatColor.translateAlternateColorCodes('&', name);
		meta.setDisplayName(name);

		return this;
	}

	public ItemBuilder setLore(List<String> lore) {
		
		List<String> coloredLore = new ArrayList<String>(lore.size());
		for(String str:lore){
			coloredLore.add(ChatColor.translateAlternateColorCodes('&', str));
		}
		meta.setLore(coloredLore);

		return this;
	}

	public ItemBuilder addEnchant(Enchantment enchantment, int value, boolean bool) {

		meta.addEnchant(enchantment, value, bool);

		return this;
	}

	public ItemBuilder removeEnchant(Enchantment enchantment) {

		meta.removeEnchant(enchantment);

		return this;
	}

	public ItemBuilder addItemFlag(ItemFlag flag) {

		meta.addItemFlags(flag);

		return this;

	}

	public ItemBuilder removeItemFlag(ItemFlag flag) {

		meta.removeItemFlags(flag);

		return this;

	}

	public ItemBuilder setDurablility(short durability) {

		item.setDurability(durability);

		return this;
	}

	public ItemBuilder setUnbreakable(boolean unbreakable) {

		meta.spigot().setUnbreakable(unbreakable);

		return this;
	}

	public ItemBuilder setMaterialData(MaterialData data) {

		item.setData(data);

		return this;
	}

	public ItemBuilder setLeatherArmorColor(Color color) throws IllegalArgumentException{

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

	public ItemBuilder set(String... page) {

		BookMeta bookMeta = (BookMeta) meta;
		bookMeta.addPage(page);
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

	public ItemBuilder setNBTTagCompound(String NBTTagType) {

		net.minecraft.server.v1_8_R3.ItemStack stack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
		tag.set(NBTTagType, new NBTTagByte((byte) 1));
		stack.setTag(tag);
		item = CraftItemStack.asBukkitCopy(stack);

		return this;
	}

	public String getName() {

		return meta.getDisplayName();

	}

	public Map<Enchantment, Integer> getEnchants() {

		return meta.getEnchants();

	}

	public List<String> getLore() {

		return meta.getLore();

	}

	public Set<ItemFlag> getItemFlags() {

		return meta.getItemFlags();

	}

	public String getBookPage(int pageNumber) {

		return ((BookMeta) meta).getPage(pageNumber);

	}

	public int getBookPageCount() {

		return ((BookMeta) meta).getPages().size();

	}

	public List<String> getBookPages() {

		return ((BookMeta) meta).getPages();

	}

	public String getBookAuthor() {

		return ((BookMeta) meta).getAuthor();

	}

	public String getBookTitle() {

		return ((BookMeta) meta).getTitle();

	}

	public List<Pattern> getBannerPatterns() {

		return ((BannerMeta) meta).getPatterns();

	}

	public DyeColor getBannerBaseColor() {

		return ((BannerMeta) meta).getBaseColor();

	}

	public boolean hasItemFlag(ItemFlag flag) {

		return(getItemFlags().contains(flag));

	}

	public boolean hasEnchant(Enchantment enchant) {

		return (!getEnchants().containsKey(enchant));

	}

	public boolean hasLore() {
		
		return meta.hasLore();
		
	}

	public boolean hasBookAuthor() {
		
		return ((BookMeta) meta).hasAuthor();
		
	}

	public boolean hasBookPages() {
		
		return ((BookMeta) meta).hasPages();
		
	}

	public boolean hasBookTitle() {
		
		return ((BookMeta) meta).hasTitle();
		
	}

	public ItemStack clone() {

		return item.clone();

	}
	
	public ItemStack build(){
		
		item.setItemMeta(meta);
		return item;
		
	}
}
