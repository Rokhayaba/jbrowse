/**
 * A JBrowse component keeps a reference to the main browser object, and is configurable.
 */

define([
           'dojo/_base/declare',
           'dojo/_base/lang',
           'JBrowse/Util'
       ],
       function(
           declare,
           lang,
           Util
       ) {

return declare( null, {

    constructor: function( args ) {
        args = args || {};

        this.browser = args.browser;
        if( ! this.browser )
            throw "a reference to the main browser is required by this constructor";

        this.configLocal = args.configLocal || {};
        this.configBase = args.configBase || args.config || {};
        // merges the defaults, the base, and the diff configs
        this.config = this._initialConfig();

        // cache for compiled configuration values
        this.compiledConfig = {};
    },

    _initialConfig: function( config ) {
        return this._mergeConfigs(
            lang.clone( this.configBase = this._mergeConfigs( dojo.clone( this._defaultConfig() ), this.configBase ) ),
            this.configLocal
        );
    },

    _defaultConfig: function() {
        return {
            baseUrl: '/',
            region: 'center'
        };
    },

    /**
     * Return an object containing all information that should be
     * saved in local storage across browser reloads in order to
     * reconstruct the current state of this object.
     */
    getConfigLocal: function( additional ) {
        function filter( base, local ) {
            for( var k in local ) {
                if( typeof local[k] == 'object' && ! lang.isArray( local[k] ) && base )
                    filter( base[k], local[k] );
                else if( base && base[k] === local[k] )
                    delete local[k];
            }
            return local;
        }

        return this.configLocal = filter( this.configBase, lang.mixin( lang.mixin( {}, this.configLocal ), additional ) );
    },

    setConfig: function( pathstr, value ) {

        function set (conf,diff,base,path,value ) {
            if( path.length > 1 ) {
                var k = path.shift();
                if( ! conf[k] ) conf[k] = {};
                if( ! diff[k] ) diff[k] = {};
                if( ! base[k] ) base[k] = {};
                set( conf[k], diff[k], base[k], path, value );
            }
            else {
                var k = path[0];
                conf[ k ] = value;
                if( base[ k ] != value ) {
                    delete this.compiledConfig[ pathstr ];
                    diff[ k ] = value;
                }
            }
        }

        set.call( this,
                  this.config,
                  this.configLocal,
                  this.configBase,
                  pathstr.split('.'),
                  value
                );

        return value;
    },

    resolveUrl: function( url, args ) {
        args = args || {};
        return Util.resolveUrl(
            this.getConf('baseUrl',[]),
            this.fillTemplate( url, args )
        );

    },

    _mergeConfigs: function(a, b) {
        if( b === null )
            return null;
        if( a === null )
            a = {};

        for (var prop in b) {
            if ((prop in a)
                && ("object" == typeof b[prop])
                && ("object" == typeof a[prop]) ) {
                a[prop] = this._mergeConfigs( a[prop], b[prop] );
            } else if( typeof a[prop] == 'undefined' || typeof b[prop] != 'undefined' ){
                a[prop] = b[prop];
            }
        }
        return a;
    },

    _compileConfigurationPath: function( path ) {
        var confVal = this.config;

        if( typeof path == 'string' )
            path = path.split('.');
        while( path.length && confVal )
            confVal = confVal[ path.shift() ];

        if( path.length )
            return function() { return null; };

        return typeof confVal == 'function'
            ? confVal
            : function() { return confVal; };
    },

    /**
     * Given a dot-separated string configuration path into the config
     * (e.g. "style.bg_color"), get the value of the configuration.
     *
     * If args are given, evaluate the configuration using them.
     * Otherwise, return a function that returns the value of the
     * configuration when called.
     */
    getConf: function( path, args ) {
        var func = this.compiledConfig[path];
        if( ! func ) {
            func = this.compiledConfig[path] = this._compileConfigurationPath( path );
        }

        return args ? func.apply( this, args ) : func.call( this );
    },

    /**
     * Given a string with templating strings like {refseq}, fill them
     * in using the given values.
     *
     * With no additional values given, knows how to interpolate
     * {refseq}, {refSeq}, {refSeqNum}, and {refSeqNumNoLeadingZeroes}.
     *
     * @param {String} str string to interpolate values into
     * @param {Object} values optional object with additional values that can be interpolated
     * @returns new string with interpolations
     */
    fillTemplate: function( str, values ) {

        // skip if it's not a string or the string has no interpolations
        if( typeof str != 'string' || str.indexOf('{') == -1 )
            return str;

        // fill in a bunch of args for this.refSeq or this.ref
        var templateFillArgs = {
            'refseq': (this.refSeq||{}).name || (this.ref||{}).name || this.ref || ''
        };
        templateFillArgs.refSeq = templateFillArgs.refseq;

        if( templateFillArgs.refSeq ) {
            templateFillArgs.refSeqNum = ( /\d+/.exec( templateFillArgs.refSeq ) || [] )[0] || '';
        }
        // make refseqNumNoLeadingZeroes
        if( templateFillArgs.refSeqNum ) {
            templateFillArgs.refSeqNumNoLeadingZeroes = ( /^0*(\d+)/.exec( templateFillArgs.refSeqNum ) || [] )[1] || '';
        }

        if( values )
            lang.mixin( templateFillArgs, values );

        return Util.fillTemplate( str, templateFillArgs );
    }
});
});