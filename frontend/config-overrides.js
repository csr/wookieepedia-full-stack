const { override, babelInclude, addBabelPlugin } = require('customize-cra');
const path = require('path');

module.exports = override(
  babelInclude([path.resolve('src')]),
  addBabelPlugin([
    'babel-plugin-module-resolver',
    {
      root: ['./src'],
      alias: {
        '@': './src',
      },
    },
  ]),
);
