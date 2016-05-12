package us.universalpvp.te.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;

public class ItemBuilder {

	private ItemStack item = null;
	private ItemMeta meta = null;
	private boolean itemVisibility;

	private final boolean hasItemMeta;

	public ItemBuilder(ItemStack item) {
		this.item = item;
		this.hasItemMeta = item.hasItemMeta();
		this.createItem(item);
	}

	public ItemBuilder(Material material) {
		this(new ItemStack(material));
	}

	private ItemBuilder createItem(ItemStack item) {

		item = new ItemStack(item);
		this.item = item;

		return this;
	}

	public ItemBuilder setName(String name) {

		throwItemMetaException();
		ItemMeta meta = this.item.getItemMeta();
		this.meta = meta;
		name = ChatColor.translateAlternateColorCodes('&', name);
		meta.setDisplayName(name);

		return this;
	}

	public ItemBuilder setLore(List<String> lore) {

		throwItemMetaException();

		ItemMeta meta = this.item.getItemMeta();
		this.meta = meta;
		meta.setLore(lore);

		return this;
	}

	public ItemBuilder addEnchant(Enchantment enchantment, int value, boolean bool) {

		throwItemMetaException();

		ItemMeta meta = this.item.getItemMeta();
		this.meta = meta;
		meta.addEnchant(enchantment, value, bool);

		return this;
	}

	public ItemBuilder removeEnchant(Enchantment enchantment) {

		throwItemMetaException();

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

	public ItemBuilder setDurablility(double d) {

		item.setDurability((short) d);

		return this;
	}

	public ItemBuilder setUnbreakable(boolean unbreakable) {

		ItemMeta meta = this.item.getItemMeta();
		this.meta = meta;
		meta.spigot().setUnbreakable(true);

		return this;
	}

	public ItemBuilder setMaterialData(MaterialData data) {

		throwItemMetaException();
		item.setData(data);

		return this;
	}

	public ItemBuilder setLeatherArmorColor(Color color) {

		throwItemMetaException();
		throwUnsupportedMetaException(LeatherArmorMeta.class,
				"Cannot change the color of a non-leather armor ItemStack");
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

		throwItemMetaException();
		throwUnsupportedMetaException(BookMeta.class, "Cannot set the banner base color to a non-banner ItemStack");

		BookMeta bookMeta = (BookMeta) meta;
		bookMeta.addPage(page);
		item.setItemMeta(bookMeta);

		return this;
	}

	public ItemBuilder setBookPage(int page, String data) {

		throwItemMetaException();
		throwUnsupportedMetaException(BookMeta.class, "Cannot set the banner base color to a non-banner ItemStack");

		BookMeta bookMeta = (BookMeta) meta;
		bookMeta.setPage(page, data);
		item.setItemMeta(bookMeta);

		return this;
	}

	public ItemBuilder setTitle(String title) {

		throwItemMetaException();
		throwUnsupportedMetaException(BannerMeta.class, "Cannot set the banner base color to a non-banner ItemStack");

		BookMeta bookMeta = (BookMeta) meta;
		bookMeta.setTitle(title);
		item.setItemMeta(bookMeta);

		return this;
	}

	public ItemBuilder setBookOwner(String player) {

		BookMeta bookMeta = (BookMeta) meta;
		bookMeta.setAuthor(player);

		return this;
	}

	public ItemBuilder setBookOwner(Player player) {

		BookMeta bookMeta = (BookMeta) meta;
		bookMeta.setAuthor(player.getName());

		return this;
	}

	public ItemBuilder setBookOwner(UUID uuid) {

		BookMeta bookMeta = (BookMeta) meta;
		bookMeta.setAuthor(Bukkit.getPlayer(uuid).toString());

		return this;
	}

	public ItemBuilder setBannerPattern(int index, Pattern pattern) {

		throwItemMetaException();
		throwUnsupportedMetaException(BannerMeta.class, "Cannot set the banner base color to a non-banner ItemStack");

		BannerMeta bannerMeta = (BannerMeta) meta;
		bannerMeta.setPattern(index, pattern);
		item.setItemMeta(bannerMeta);

		return this;
	}

	public ItemBuilder setBannerBaseColor(DyeColor color) {

		throwItemMetaException();
		throwUnsupportedMetaException(BannerMeta.class, "Cannot set the banner base color to a non-banner ItemStack");

		BannerMeta bannerMeta = (BannerMeta) meta;
		bannerMeta.setBaseColor(color);
		item.setItemMeta(bannerMeta);

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

	public ItemBuilder setItemDropNameVisibile(boolean bool) {

		this.itemVisibility = bool;

		return this;
	}

	public ItemBuilder setGlowing(boolean bool) {

		ItemMeta meta = item.getItemMeta();
		this.meta = meta;

		if (!item.getItemMeta().hasEnchants()) {
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		} else {

			net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
			NBTTagCompound tag = null;
			if (!nmsStack.hasTag()) {
				tag = new NBTTagCompound();
				nmsStack.setTag(tag);
			}
			if (tag == null)
				tag = nmsStack.getTag();
			NBTTagList ench = new NBTTagList();
			tag.set("ench", ench);
			nmsStack.setTag(tag);

		}

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

	public void buildItem() {

		item.setItemMeta(this.meta);

	}

	private void throwItemMetaException() {
		if (!(hasItemMeta)) {
			throw new UnsupportedOperationException(
					"Material of type " + item.getType().toString() + " cannot have ItemMeta assigned to it.");
		}
	}

	private void throwUnsupportedMetaException(final Class<?> clazz, final String error) {
		if (!(clazz.isAssignableFrom(meta.getClass()))) {
			throw new UnsupportedOperationException(error);
		}
	}

	@EventHandler
	protected void setItemDropNameVisible(PlayerDropItemEvent e) {

		Entity entity = e.getItemDrop();

		if (itemVisibility == true) {
			if (entity.getName() != meta.getDisplayName()) {
				return;
			} else {

				entity.setCustomName(entity.getName());
				entity.setCustomNameVisible(true);

			}
		}
	}

}
