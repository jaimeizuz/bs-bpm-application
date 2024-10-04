import React, { useCallback, useEffect, useState } from 'react';
import { Checkbox, FormGroup, TextInput } from '@patternfly/react-core';
const Form__UserTaskProcess_firstUserTask: React.FC<any> = (props: any) => {
	const [formApi, setFormApi] = useState<any>();
	const [bVar3, set__bVar3] = useState<boolean>(false);
	const [iVar2, set__iVar2] = useState<number>();
	const [sVar1, set__sVar1] = useState<string>('');
	/* Utility function that fills the form with the data received from the kogito runtime */
	const setFormData = (data) => {
		if (!data) {
			return;
		}
		set__bVar3(data?.bVar3 ?? false);
		set__iVar2(data?.iVar2);
		set__sVar1(data?.sVar1 ?? '');
	};
	/* Utility function to generate the expected form output as a json object */
	const getFormData = useCallback(() => {
		const formData: any = {};
		formData.bVar3 = bVar3;
		formData.iVar2 = iVar2;
		formData.sVar1 = sVar1;
		return formData;
	}, [bVar3, iVar2, sVar1]);
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
			<FormGroup fieldId='uniforms-0008-0001'>
				<Checkbox
					isChecked={bVar3}
					isDisabled={false}
					id={'uniforms-0008-0001'}
					name={'bVar3'}
					label={'B var 3'}
					onChange={set__bVar3}
				/>
			</FormGroup>
			<FormGroup
				fieldId={'uniforms-0008-0003'}
				label={'I var 2'}
				isRequired={false}>
				<TextInput
					type={'number'}
					name={'iVar2'}
					isDisabled={false}
					id={'uniforms-0008-0003'}
					placeholder={''}
					step={1}
					value={iVar2}
					onChange={(newValue) => set__iVar2(Number(newValue))}
				/>
			</FormGroup>
			<FormGroup
				fieldId={'uniforms-0008-0004'}
				label={'S var 1'}
				isRequired={false}>
				<TextInput
					name={'sVar1'}
					id={'uniforms-0008-0004'}
					isDisabled={false}
					placeholder={''}
					type={'text'}
					value={sVar1}
					onChange={set__sVar1}
				/>
			</FormGroup>
		</div>
	);
};
export default Form__UserTaskProcess_firstUserTask;
