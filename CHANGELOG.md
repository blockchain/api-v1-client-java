# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

## [2.0.0] - 2017-06-16
### Added
- This CHANGELOG file to record all the changes in current and following version of this project.
- Filter, limit, and offset optional parameters to getAddress in BlockExplorer.
- Confirms and limit optional parameters to getUnspentOutputs in BlockExplorer.
- A new getBalance method in BlockExplorer.
- A new getMultiAddress method in BlockExplorer.
- A new getXpub method in BlockExplorer.
- A new toFiat method in ExchangeRates.
- A new checkGap method in Receive.
- A new getCallbackLog method in Receive.
- A new getChart method in Statistics.
- A new getPools method in Statistics.

### Changed
- ExchangeRate series interfaces to allow API be used for all methods.
- Receive series interfaces to allow API be used for all methods.
- Statistics series interfaces to allow API be used for all methods.
- Moved createWallet functions from CreateWallet class to Wallet class.

### Deprecated
- “Get transaction and block by index” interfaces in BlockExplorer.

### Removed
- Method getInventoryData in BlockExplorer
- Parameter “note” from send and sendMany methods in Wallet.
- Parameter “confirmations” from listAddress and getAddress method in Wallet.
- Method consolidate in Wallet.
