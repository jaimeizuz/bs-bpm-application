import React, { useCallback, useEffect, useState } from 'react';
import {
	Card,
	CardBody,
	Alert,
	FormGroup,
	Checkbox,
} from '@patternfly/react-core';
const Form__ServiceTaskProcess: React.FC<any> = (props: any) => {
	const [formApi, setFormApi] = useState<any>();
	const [countriesList__countriesList, set__countriesList__countriesList] =
		useState<any[]>();
	const [validCountries, set__validCountries] = useState<boolean>(false);
	/* Utility function that fills the form with the data received from the kogito runtime */
	const setFormData = (data) => {
		if (!data) {
			return;
		}
		set__countriesList__countriesList(data?.countriesList?.countriesList);
		set__validCountries(data?.validCountries ?? false);
	};
	/* Utility function to generate the expected form output as a json object */
	const getFormData = useCallback(() => {
		const formData: any = {};
		formData.countriesList = {};
		formData.countriesList.countriesList = countriesList__countriesList;
		formData.validCountries = validCountries;
		return formData;
	}, [countriesList__countriesList, validCountries]);
	/* Utility function to validate the form on the 'beforeSubmit' Lifecycle Hook */
	const validateForm = useCallback(() => {}, []);
	/* Utility function to perform actions on the on the 'afterSubmit' Lifecycle Hook */
	const afterSubmit = useCallback((result) => {}, []);
	useEffect(() => {
		if (formApi) {
			/*
        Form Lifecycle Hook that will be executed before the form is submitted.
        Throwing an error will stop the form submit. Usually should be used to validate the form.
      */
			formApi.beforeSubmit = () => validateForm();
			/*
        Form Lifecycle Hook that will be executed after the form is submitted.
        It will receive a response object containing the `type` flag indicating if the submit has been successful and `info` with extra information about the submit result.
      */
			formApi.afterSubmit = (result) => afterSubmit(result);
			/* Generates the expected form output object to be posted */
			formApi.getFormData = () => getFormData();
		}
	}, [getFormData, validateForm, afterSubmit]);
	useEffect(() => {
		/*
      Call to the Kogito console form engine. It will establish the connection with the console embeding the form
      and return an instance of FormAPI that will allow hook custom code into the form lifecycle.
      The `window.Form.openForm` call expects an object with the following entries:
        - onOpen: Callback that will be called after the connection with the console is established. The callback
        will receive the following arguments:
          - data: the data to be bound into the form
          - ctx: info about the context where the form is being displayed. This will contain information such as the form JSON Schema, process/task, user...
    */
		const api = window.Form.openForm({
			onOpen: (data, context) => {
				setFormData(data);
			},
		});
		setFormApi(api);
	}, []);
	return (
		<div className={'pf-c-form'}>
			<Card>
				<CardBody className='pf-c-form'>
					<label>
						<b>Countries list</b>
					</label>
					<FormGroup
						fieldId={'uniforms-0002-0003'}
						label={'Countries list'}
						isRequired={false}>
						<Alert variant='warning' title='Unsupported field type: Array'>
							Cannot find form control for property{' '}
							<code>countriesList.countriesList</code> with type{' '}
							<code>Array</code>:<br />
							Some complex property types, such as{' '}
							<code>Array&lt;object&gt;</code> aren't yet supported, however,
							you can still write your own component into the form and use the
							already existing states{' '}
							<code>
								const [ countriesList__countriesList,
								set__countriesList__countriesList ]
							</code>
							.
						</Alert>
					</FormGroup>
				</CardBody>
			</Card>
			<FormGroup fieldId='uniforms-0002-0005'>
				<Checkbox
					isChecked={validCountries}
					isDisabled={false}
					id={'uniforms-0002-0005'}
					name={'validCountries'}
					label={'Valid countries'}
					onChange={set__validCountries}
				/>
			</FormGroup>
		</div>
	);
};
export default Form__ServiceTaskProcess;
