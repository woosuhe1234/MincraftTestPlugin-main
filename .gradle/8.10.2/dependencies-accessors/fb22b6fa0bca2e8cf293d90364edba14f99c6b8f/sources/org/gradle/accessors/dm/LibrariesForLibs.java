package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the {@code libs} extension.
 */
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final ComLibraryAccessors laccForComLibraryAccessors = new ComLibraryAccessors(owner);
    private final NetLibraryAccessors laccForNetLibraryAccessors = new NetLibraryAccessors(owner);
    private final OrgLibraryAccessors laccForOrgLibraryAccessors = new OrgLibraryAccessors(owner);
    private final SystemsLibraryAccessors laccForSystemsLibraryAccessors = new SystemsLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

    /**
     * Group of libraries at <b>com</b>
     */
    public ComLibraryAccessors getCom() {
        return laccForComLibraryAccessors;
    }

    /**
     * Group of libraries at <b>net</b>
     */
    public NetLibraryAccessors getNet() {
        return laccForNetLibraryAccessors;
    }

    /**
     * Group of libraries at <b>org</b>
     */
    public OrgLibraryAccessors getOrg() {
        return laccForOrgLibraryAccessors;
    }

    /**
     * Group of libraries at <b>systems</b>
     */
    public SystemsLibraryAccessors getSystems() {
        return laccForSystemsLibraryAccessors;
    }

    /**
     * Group of versions at <b>versions</b>
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Group of bundles at <b>bundles</b>
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Group of plugins at <b>plugins</b>
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    public static class ComLibraryAccessors extends SubDependencyFactory {
        private final ComDestroystokyoLibraryAccessors laccForComDestroystokyoLibraryAccessors = new ComDestroystokyoLibraryAccessors(owner);
        private final ComFastasyncworldeditLibraryAccessors laccForComFastasyncworldeditLibraryAccessors = new ComFastasyncworldeditLibraryAccessors(owner);
        private final ComGithubLibraryAccessors laccForComGithubLibraryAccessors = new ComGithubLibraryAccessors(owner);
        private final ComNisovinLibraryAccessors laccForComNisovinLibraryAccessors = new ComNisovinLibraryAccessors(owner);
        private final ComSk89qLibraryAccessors laccForComSk89qLibraryAccessors = new ComSk89qLibraryAccessors(owner);

        public ComLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.destroystokyo</b>
         */
        public ComDestroystokyoLibraryAccessors getDestroystokyo() {
            return laccForComDestroystokyoLibraryAccessors;
        }

        /**
         * Group of libraries at <b>com.fastasyncworldedit</b>
         */
        public ComFastasyncworldeditLibraryAccessors getFastasyncworldedit() {
            return laccForComFastasyncworldeditLibraryAccessors;
        }

        /**
         * Group of libraries at <b>com.github</b>
         */
        public ComGithubLibraryAccessors getGithub() {
            return laccForComGithubLibraryAccessors;
        }

        /**
         * Group of libraries at <b>com.nisovin</b>
         */
        public ComNisovinLibraryAccessors getNisovin() {
            return laccForComNisovinLibraryAccessors;
        }

        /**
         * Group of libraries at <b>com.sk89q</b>
         */
        public ComSk89qLibraryAccessors getSk89q() {
            return laccForComSk89qLibraryAccessors;
        }

    }

    public static class ComDestroystokyoLibraryAccessors extends SubDependencyFactory {
        private final ComDestroystokyoPaperLibraryAccessors laccForComDestroystokyoPaperLibraryAccessors = new ComDestroystokyoPaperLibraryAccessors(owner);

        public ComDestroystokyoLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.destroystokyo.paper</b>
         */
        public ComDestroystokyoPaperLibraryAccessors getPaper() {
            return laccForComDestroystokyoPaperLibraryAccessors;
        }

    }

    public static class ComDestroystokyoPaperLibraryAccessors extends SubDependencyFactory {
        private final ComDestroystokyoPaperPaperLibraryAccessors laccForComDestroystokyoPaperPaperLibraryAccessors = new ComDestroystokyoPaperPaperLibraryAccessors(owner);

        public ComDestroystokyoPaperLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.destroystokyo.paper.paper</b>
         */
        public ComDestroystokyoPaperPaperLibraryAccessors getPaper() {
            return laccForComDestroystokyoPaperPaperLibraryAccessors;
        }

    }

    public static class ComDestroystokyoPaperPaperLibraryAccessors extends SubDependencyFactory {

        public ComDestroystokyoPaperPaperLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>com.destroystokyo.paper:paper-api</b> coordinates and
         * with version reference <b>com.destroystokyo.paper.paper.api</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("com.destroystokyo.paper.paper.api");
        }

    }

    public static class ComFastasyncworldeditLibraryAccessors extends SubDependencyFactory {
        private final ComFastasyncworldeditFastasyncworldeditLibraryAccessors laccForComFastasyncworldeditFastasyncworldeditLibraryAccessors = new ComFastasyncworldeditFastasyncworldeditLibraryAccessors(owner);

        public ComFastasyncworldeditLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.fastasyncworldedit.fastasyncworldedit</b>
         */
        public ComFastasyncworldeditFastasyncworldeditLibraryAccessors getFastasyncworldedit() {
            return laccForComFastasyncworldeditFastasyncworldeditLibraryAccessors;
        }

    }

    public static class ComFastasyncworldeditFastasyncworldeditLibraryAccessors extends SubDependencyFactory {

        public ComFastasyncworldeditFastasyncworldeditLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>bukkit</b> with <b>com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit</b> coordinates and
         * with version reference <b>com.fastasyncworldedit.fastasyncworldedit.bukkit</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getBukkit() {
            return create("com.fastasyncworldedit.fastasyncworldedit.bukkit");
        }

    }

    public static class ComGithubLibraryAccessors extends SubDependencyFactory {
        private final ComGithubMilkbowlLibraryAccessors laccForComGithubMilkbowlLibraryAccessors = new ComGithubMilkbowlLibraryAccessors(owner);

        public ComGithubLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.github.milkbowl</b>
         */
        public ComGithubMilkbowlLibraryAccessors getMilkbowl() {
            return laccForComGithubMilkbowlLibraryAccessors;
        }

    }

    public static class ComGithubMilkbowlLibraryAccessors extends SubDependencyFactory {

        public ComGithubMilkbowlLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>vaultapi</b> with <b>com.github.MilkBowl:VaultAPI</b> coordinates and
         * with version reference <b>com.github.milkbowl.vaultapi</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getVaultapi() {
            return create("com.github.milkbowl.vaultapi");
        }

    }

    public static class ComNisovinLibraryAccessors extends SubDependencyFactory {
        private final ComNisovinMagicspellsLibraryAccessors laccForComNisovinMagicspellsLibraryAccessors = new ComNisovinMagicspellsLibraryAccessors(owner);

        public ComNisovinLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.nisovin.magicspells</b>
         */
        public ComNisovinMagicspellsLibraryAccessors getMagicspells() {
            return laccForComNisovinMagicspellsLibraryAccessors;
        }

    }

    public static class ComNisovinMagicspellsLibraryAccessors extends SubDependencyFactory {

        public ComNisovinMagicspellsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>magicspells</b> with <b>com.nisovin.magicspells:MagicSpells</b> coordinates and
         * with version reference <b>com.nisovin.magicspells.magicspells</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getMagicspells() {
            return create("com.nisovin.magicspells.magicspells");
        }

    }

    public static class ComSk89qLibraryAccessors extends SubDependencyFactory {
        private final ComSk89qWorldeditLibraryAccessors laccForComSk89qWorldeditLibraryAccessors = new ComSk89qWorldeditLibraryAccessors(owner);

        public ComSk89qLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.sk89q.worldedit</b>
         */
        public ComSk89qWorldeditLibraryAccessors getWorldedit() {
            return laccForComSk89qWorldeditLibraryAccessors;
        }

    }

    public static class ComSk89qWorldeditLibraryAccessors extends SubDependencyFactory {
        private final ComSk89qWorldeditWorldeditLibraryAccessors laccForComSk89qWorldeditWorldeditLibraryAccessors = new ComSk89qWorldeditWorldeditLibraryAccessors(owner);

        public ComSk89qWorldeditLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.sk89q.worldedit.worldedit</b>
         */
        public ComSk89qWorldeditWorldeditLibraryAccessors getWorldedit() {
            return laccForComSk89qWorldeditWorldeditLibraryAccessors;
        }

    }

    public static class ComSk89qWorldeditWorldeditLibraryAccessors extends SubDependencyFactory {

        public ComSk89qWorldeditWorldeditLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>bukkit</b> with <b>com.sk89q.worldedit:worldedit-bukkit</b> coordinates and
         * with version reference <b>com.sk89q.worldedit.worldedit.bukkit</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getBukkit() {
            return create("com.sk89q.worldedit.worldedit.bukkit");
        }

    }

    public static class NetLibraryAccessors extends SubDependencyFactory {
        private final NetCitizensnpcsLibraryAccessors laccForNetCitizensnpcsLibraryAccessors = new NetCitizensnpcsLibraryAccessors(owner);

        public NetLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>net.citizensnpcs</b>
         */
        public NetCitizensnpcsLibraryAccessors getCitizensnpcs() {
            return laccForNetCitizensnpcsLibraryAccessors;
        }

    }

    public static class NetCitizensnpcsLibraryAccessors extends SubDependencyFactory {
        private final NetCitizensnpcsCitizensLibraryAccessors laccForNetCitizensnpcsCitizensLibraryAccessors = new NetCitizensnpcsCitizensLibraryAccessors(owner);

        public NetCitizensnpcsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>net.citizensnpcs.citizens</b>
         */
        public NetCitizensnpcsCitizensLibraryAccessors getCitizens() {
            return laccForNetCitizensnpcsCitizensLibraryAccessors;
        }

    }

    public static class NetCitizensnpcsCitizensLibraryAccessors extends SubDependencyFactory {

        public NetCitizensnpcsCitizensLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>main</b> with <b>net.citizensnpcs:citizens-main</b> coordinates and
         * with version reference <b>net.citizensnpcs.citizens.main</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getMain() {
            return create("net.citizensnpcs.citizens.main");
        }

    }

    public static class OrgLibraryAccessors extends SubDependencyFactory {
        private final OrgJetbrainsLibraryAccessors laccForOrgJetbrainsLibraryAccessors = new OrgJetbrainsLibraryAccessors(owner);
        private final OrgMongodbLibraryAccessors laccForOrgMongodbLibraryAccessors = new OrgMongodbLibraryAccessors(owner);
        private final OrgPegdownLibraryAccessors laccForOrgPegdownLibraryAccessors = new OrgPegdownLibraryAccessors(owner);

        public OrgLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.jetbrains</b>
         */
        public OrgJetbrainsLibraryAccessors getJetbrains() {
            return laccForOrgJetbrainsLibraryAccessors;
        }

        /**
         * Group of libraries at <b>org.mongodb</b>
         */
        public OrgMongodbLibraryAccessors getMongodb() {
            return laccForOrgMongodbLibraryAccessors;
        }

        /**
         * Group of libraries at <b>org.pegdown</b>
         */
        public OrgPegdownLibraryAccessors getPegdown() {
            return laccForOrgPegdownLibraryAccessors;
        }

    }

    public static class OrgJetbrainsLibraryAccessors extends SubDependencyFactory {

        public OrgJetbrainsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>annotations</b> with <b>org.jetbrains:annotations</b> coordinates and
         * with version reference <b>org.jetbrains.annotations</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAnnotations() {
            return create("org.jetbrains.annotations");
        }

    }

    public static class OrgMongodbLibraryAccessors extends SubDependencyFactory {
        private final OrgMongodbMongodbLibraryAccessors laccForOrgMongodbMongodbLibraryAccessors = new OrgMongodbMongodbLibraryAccessors(owner);

        public OrgMongodbLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.mongodb.mongodb</b>
         */
        public OrgMongodbMongodbLibraryAccessors getMongodb() {
            return laccForOrgMongodbMongodbLibraryAccessors;
        }

    }

    public static class OrgMongodbMongodbLibraryAccessors extends SubDependencyFactory {
        private final OrgMongodbMongodbDriverLibraryAccessors laccForOrgMongodbMongodbDriverLibraryAccessors = new OrgMongodbMongodbDriverLibraryAccessors(owner);

        public OrgMongodbMongodbLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.mongodb.mongodb.driver</b>
         */
        public OrgMongodbMongodbDriverLibraryAccessors getDriver() {
            return laccForOrgMongodbMongodbDriverLibraryAccessors;
        }

    }

    public static class OrgMongodbMongodbDriverLibraryAccessors extends SubDependencyFactory {

        public OrgMongodbMongodbDriverLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>sync</b> with <b>org.mongodb:mongodb-driver-sync</b> coordinates and
         * with version reference <b>org.mongodb.mongodb.driver.sync</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getSync() {
            return create("org.mongodb.mongodb.driver.sync");
        }

    }

    public static class OrgPegdownLibraryAccessors extends SubDependencyFactory {

        public OrgPegdownLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>pegdown</b> with <b>org.pegdown:pegdown</b> coordinates and
         * with version reference <b>org.pegdown.pegdown</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getPegdown() {
            return create("org.pegdown.pegdown");
        }

    }

    public static class SystemsLibraryAccessors extends SubDependencyFactory {
        private final SystemsManifoldLibraryAccessors laccForSystemsManifoldLibraryAccessors = new SystemsManifoldLibraryAccessors(owner);

        public SystemsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>systems.manifold</b>
         */
        public SystemsManifoldLibraryAccessors getManifold() {
            return laccForSystemsManifoldLibraryAccessors;
        }

    }

    public static class SystemsManifoldLibraryAccessors extends SubDependencyFactory {
        private final SystemsManifoldManifoldLibraryAccessors laccForSystemsManifoldManifoldLibraryAccessors = new SystemsManifoldManifoldLibraryAccessors(owner);

        public SystemsManifoldLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>systems.manifold.manifold</b>
         */
        public SystemsManifoldManifoldLibraryAccessors getManifold() {
            return laccForSystemsManifoldManifoldLibraryAccessors;
        }

    }

    public static class SystemsManifoldManifoldLibraryAccessors extends SubDependencyFactory {

        public SystemsManifoldManifoldLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ext</b> with <b>systems.manifold:manifold-ext</b> coordinates and
         * with version reference <b>systems.manifold.manifold.ext</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getExt() {
            return create("systems.manifold.manifold.ext");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        private final ComVersionAccessors vaccForComVersionAccessors = new ComVersionAccessors(providers, config);
        private final NetVersionAccessors vaccForNetVersionAccessors = new NetVersionAccessors(providers, config);
        private final OrgVersionAccessors vaccForOrgVersionAccessors = new OrgVersionAccessors(providers, config);
        private final SystemsVersionAccessors vaccForSystemsVersionAccessors = new SystemsVersionAccessors(providers, config);
        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com</b>
         */
        public ComVersionAccessors getCom() {
            return vaccForComVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.net</b>
         */
        public NetVersionAccessors getNet() {
            return vaccForNetVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.org</b>
         */
        public OrgVersionAccessors getOrg() {
            return vaccForOrgVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.systems</b>
         */
        public SystemsVersionAccessors getSystems() {
            return vaccForSystemsVersionAccessors;
        }

    }

    public static class ComVersionAccessors extends VersionFactory  {

        private final ComDestroystokyoVersionAccessors vaccForComDestroystokyoVersionAccessors = new ComDestroystokyoVersionAccessors(providers, config);
        private final ComFastasyncworldeditVersionAccessors vaccForComFastasyncworldeditVersionAccessors = new ComFastasyncworldeditVersionAccessors(providers, config);
        private final ComGithubVersionAccessors vaccForComGithubVersionAccessors = new ComGithubVersionAccessors(providers, config);
        private final ComNisovinVersionAccessors vaccForComNisovinVersionAccessors = new ComNisovinVersionAccessors(providers, config);
        private final ComSk89qVersionAccessors vaccForComSk89qVersionAccessors = new ComSk89qVersionAccessors(providers, config);
        public ComVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.destroystokyo</b>
         */
        public ComDestroystokyoVersionAccessors getDestroystokyo() {
            return vaccForComDestroystokyoVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.com.fastasyncworldedit</b>
         */
        public ComFastasyncworldeditVersionAccessors getFastasyncworldedit() {
            return vaccForComFastasyncworldeditVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.com.github</b>
         */
        public ComGithubVersionAccessors getGithub() {
            return vaccForComGithubVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.com.nisovin</b>
         */
        public ComNisovinVersionAccessors getNisovin() {
            return vaccForComNisovinVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.com.sk89q</b>
         */
        public ComSk89qVersionAccessors getSk89q() {
            return vaccForComSk89qVersionAccessors;
        }

    }

    public static class ComDestroystokyoVersionAccessors extends VersionFactory  {

        private final ComDestroystokyoPaperVersionAccessors vaccForComDestroystokyoPaperVersionAccessors = new ComDestroystokyoPaperVersionAccessors(providers, config);
        public ComDestroystokyoVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.destroystokyo.paper</b>
         */
        public ComDestroystokyoPaperVersionAccessors getPaper() {
            return vaccForComDestroystokyoPaperVersionAccessors;
        }

    }

    public static class ComDestroystokyoPaperVersionAccessors extends VersionFactory  {

        private final ComDestroystokyoPaperPaperVersionAccessors vaccForComDestroystokyoPaperPaperVersionAccessors = new ComDestroystokyoPaperPaperVersionAccessors(providers, config);
        public ComDestroystokyoPaperVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.destroystokyo.paper.paper</b>
         */
        public ComDestroystokyoPaperPaperVersionAccessors getPaper() {
            return vaccForComDestroystokyoPaperPaperVersionAccessors;
        }

    }

    public static class ComDestroystokyoPaperPaperVersionAccessors extends VersionFactory  {

        public ComDestroystokyoPaperPaperVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.destroystokyo.paper.paper.api</b> with value <b>1.12.2-R0.1-SNAPSHOT</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getApi() { return getVersion("com.destroystokyo.paper.paper.api"); }

    }

    public static class ComFastasyncworldeditVersionAccessors extends VersionFactory  {

        private final ComFastasyncworldeditFastasyncworldeditVersionAccessors vaccForComFastasyncworldeditFastasyncworldeditVersionAccessors = new ComFastasyncworldeditFastasyncworldeditVersionAccessors(providers, config);
        public ComFastasyncworldeditVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.fastasyncworldedit.fastasyncworldedit</b>
         */
        public ComFastasyncworldeditFastasyncworldeditVersionAccessors getFastasyncworldedit() {
            return vaccForComFastasyncworldeditFastasyncworldeditVersionAccessors;
        }

    }

    public static class ComFastasyncworldeditFastasyncworldeditVersionAccessors extends VersionFactory  {

        public ComFastasyncworldeditFastasyncworldeditVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.fastasyncworldedit.fastasyncworldedit.bukkit</b> with value <b>1.17-</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getBukkit() { return getVersion("com.fastasyncworldedit.fastasyncworldedit.bukkit"); }

    }

    public static class ComGithubVersionAccessors extends VersionFactory  {

        private final ComGithubMilkbowlVersionAccessors vaccForComGithubMilkbowlVersionAccessors = new ComGithubMilkbowlVersionAccessors(providers, config);
        public ComGithubVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.github.milkbowl</b>
         */
        public ComGithubMilkbowlVersionAccessors getMilkbowl() {
            return vaccForComGithubMilkbowlVersionAccessors;
        }

    }

    public static class ComGithubMilkbowlVersionAccessors extends VersionFactory  {

        public ComGithubMilkbowlVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.github.milkbowl.vaultapi</b> with value <b>1.5.6</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getVaultapi() { return getVersion("com.github.milkbowl.vaultapi"); }

    }

    public static class ComNisovinVersionAccessors extends VersionFactory  {

        private final ComNisovinMagicspellsVersionAccessors vaccForComNisovinMagicspellsVersionAccessors = new ComNisovinMagicspellsVersionAccessors(providers, config);
        public ComNisovinVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.nisovin.magicspells</b>
         */
        public ComNisovinMagicspellsVersionAccessors getMagicspells() {
            return vaccForComNisovinMagicspellsVersionAccessors;
        }

    }

    public static class ComNisovinMagicspellsVersionAccessors extends VersionFactory  {

        public ComNisovinMagicspellsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.nisovin.magicspells.magicspells</b> with value <b>3.5-Release</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getMagicspells() { return getVersion("com.nisovin.magicspells.magicspells"); }

    }

    public static class ComSk89qVersionAccessors extends VersionFactory  {

        private final ComSk89qWorldeditVersionAccessors vaccForComSk89qWorldeditVersionAccessors = new ComSk89qWorldeditVersionAccessors(providers, config);
        public ComSk89qVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.sk89q.worldedit</b>
         */
        public ComSk89qWorldeditVersionAccessors getWorldedit() {
            return vaccForComSk89qWorldeditVersionAccessors;
        }

    }

    public static class ComSk89qWorldeditVersionAccessors extends VersionFactory  {

        private final ComSk89qWorldeditWorldeditVersionAccessors vaccForComSk89qWorldeditWorldeditVersionAccessors = new ComSk89qWorldeditWorldeditVersionAccessors(providers, config);
        public ComSk89qWorldeditVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.sk89q.worldedit.worldedit</b>
         */
        public ComSk89qWorldeditWorldeditVersionAccessors getWorldedit() {
            return vaccForComSk89qWorldeditWorldeditVersionAccessors;
        }

    }

    public static class ComSk89qWorldeditWorldeditVersionAccessors extends VersionFactory  {

        public ComSk89qWorldeditWorldeditVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.sk89q.worldedit.worldedit.bukkit</b> with value <b>6.1.9</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getBukkit() { return getVersion("com.sk89q.worldedit.worldedit.bukkit"); }

    }

    public static class NetVersionAccessors extends VersionFactory  {

        private final NetCitizensnpcsVersionAccessors vaccForNetCitizensnpcsVersionAccessors = new NetCitizensnpcsVersionAccessors(providers, config);
        public NetVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.net.citizensnpcs</b>
         */
        public NetCitizensnpcsVersionAccessors getCitizensnpcs() {
            return vaccForNetCitizensnpcsVersionAccessors;
        }

    }

    public static class NetCitizensnpcsVersionAccessors extends VersionFactory  {

        private final NetCitizensnpcsCitizensVersionAccessors vaccForNetCitizensnpcsCitizensVersionAccessors = new NetCitizensnpcsCitizensVersionAccessors(providers, config);
        public NetCitizensnpcsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.net.citizensnpcs.citizens</b>
         */
        public NetCitizensnpcsCitizensVersionAccessors getCitizens() {
            return vaccForNetCitizensnpcsCitizensVersionAccessors;
        }

    }

    public static class NetCitizensnpcsCitizensVersionAccessors extends VersionFactory  {

        public NetCitizensnpcsCitizensVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>net.citizensnpcs.citizens.main</b> with value <b>2.0.29-b2421</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getMain() { return getVersion("net.citizensnpcs.citizens.main"); }

    }

    public static class OrgVersionAccessors extends VersionFactory  {

        private final OrgJetbrainsVersionAccessors vaccForOrgJetbrainsVersionAccessors = new OrgJetbrainsVersionAccessors(providers, config);
        private final OrgMongodbVersionAccessors vaccForOrgMongodbVersionAccessors = new OrgMongodbVersionAccessors(providers, config);
        private final OrgPegdownVersionAccessors vaccForOrgPegdownVersionAccessors = new OrgPegdownVersionAccessors(providers, config);
        public OrgVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.jetbrains</b>
         */
        public OrgJetbrainsVersionAccessors getJetbrains() {
            return vaccForOrgJetbrainsVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.org.mongodb</b>
         */
        public OrgMongodbVersionAccessors getMongodb() {
            return vaccForOrgMongodbVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.org.pegdown</b>
         */
        public OrgPegdownVersionAccessors getPegdown() {
            return vaccForOrgPegdownVersionAccessors;
        }

    }

    public static class OrgJetbrainsVersionAccessors extends VersionFactory  {

        public OrgJetbrainsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.jetbrains.annotations</b> with value <b>22.0.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAnnotations() { return getVersion("org.jetbrains.annotations"); }

    }

    public static class OrgMongodbVersionAccessors extends VersionFactory  {

        private final OrgMongodbMongodbVersionAccessors vaccForOrgMongodbMongodbVersionAccessors = new OrgMongodbMongodbVersionAccessors(providers, config);
        public OrgMongodbVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.mongodb.mongodb</b>
         */
        public OrgMongodbMongodbVersionAccessors getMongodb() {
            return vaccForOrgMongodbMongodbVersionAccessors;
        }

    }

    public static class OrgMongodbMongodbVersionAccessors extends VersionFactory  {

        private final OrgMongodbMongodbDriverVersionAccessors vaccForOrgMongodbMongodbDriverVersionAccessors = new OrgMongodbMongodbDriverVersionAccessors(providers, config);
        public OrgMongodbMongodbVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.mongodb.mongodb.driver</b>
         */
        public OrgMongodbMongodbDriverVersionAccessors getDriver() {
            return vaccForOrgMongodbMongodbDriverVersionAccessors;
        }

    }

    public static class OrgMongodbMongodbDriverVersionAccessors extends VersionFactory  {

        public OrgMongodbMongodbDriverVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.mongodb.mongodb.driver.sync</b> with value <b>4.4.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getSync() { return getVersion("org.mongodb.mongodb.driver.sync"); }

    }

    public static class OrgPegdownVersionAccessors extends VersionFactory  {

        public OrgPegdownVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.pegdown.pegdown</b> with value <b>1.6.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getPegdown() { return getVersion("org.pegdown.pegdown"); }

    }

    public static class SystemsVersionAccessors extends VersionFactory  {

        private final SystemsManifoldVersionAccessors vaccForSystemsManifoldVersionAccessors = new SystemsManifoldVersionAccessors(providers, config);
        public SystemsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.systems.manifold</b>
         */
        public SystemsManifoldVersionAccessors getManifold() {
            return vaccForSystemsManifoldVersionAccessors;
        }

    }

    public static class SystemsManifoldVersionAccessors extends VersionFactory  {

        private final SystemsManifoldManifoldVersionAccessors vaccForSystemsManifoldManifoldVersionAccessors = new SystemsManifoldManifoldVersionAccessors(providers, config);
        public SystemsManifoldVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.systems.manifold.manifold</b>
         */
        public SystemsManifoldManifoldVersionAccessors getManifold() {
            return vaccForSystemsManifoldManifoldVersionAccessors;
        }

    }

    public static class SystemsManifoldManifoldVersionAccessors extends VersionFactory  {

        public SystemsManifoldManifoldVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>systems.manifold.manifold.ext</b> with value <b>2021.1.27</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getExt() { return getVersion("systems.manifold.manifold.ext"); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

    }

    public static class PluginAccessors extends PluginFactory {

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

    }

}
