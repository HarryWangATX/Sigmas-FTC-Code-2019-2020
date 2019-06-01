import React from 'react';
import PropTypes from 'prop-types';

class TextInput extends React.Component {
  constructor(props) {
    super(props);

    this.handleChange = this.handleChange.bind(this);
    this.handleKeyDown = this.handleKeyDown.bind(this);
  }

  handleChange(evt) {
    const validated = this.props.validate(evt.target.value);
    if (validated) {
      this.props.onChange(validated);
    }
  }

  handleKeyDown(evt) {
    if (this.props.onSave && evt.keyCode === 13) {
      this.props.onSave();
    }
  }

  render() {
    return (
      <input
        className={ this.props.valid ? 'valid' : 'invalid' }
        type="text"
        size={15}
        value={this.props.value}
        onChange={this.handleChange}
        onKeyDown={this.handleKeyDown}
      />
    );
  }
}

TextInput.propTypes = {
  value: PropTypes.any.isRequired,
  valid: PropTypes.bool.isRequired,
  validate: PropTypes.func.isRequired,
  onChange: PropTypes.func.isRequired,
  onSave: PropTypes.func
};

export default TextInput;
